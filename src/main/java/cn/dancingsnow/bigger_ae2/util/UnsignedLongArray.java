package cn.dancingsnow.bigger_ae2.util;

import java.util.Arrays;
import java.util.Objects;

public final class UnsignedLongArray implements Comparable<UnsignedLongArray> {
    public static final int MAX_LIMBS = 1_000_000;
    private static final long DECIMAL_BASE = 1_000_000_000L;
    private static final long LOW_INT_MASK = 0xFFFF_FFFFL;

    public static final UnsignedLongArray ZERO = new UnsignedLongArray(new long[0], true);

    private final long[] limbs;

    private UnsignedLongArray(long[] limbs, boolean normalized) {
        if (limbs.length > MAX_LIMBS) {
            throw new IllegalArgumentException("Too many unsigned integer limbs: " + limbs.length);
        }
        this.limbs = normalized ? limbs : normalize(limbs);
    }

    public static UnsignedLongArray fromLong(long value) {
        if (value < 0) {
            throw new IllegalArgumentException("Value must be non-negative: " + value);
        }
        return value == 0 ? ZERO : new UnsignedLongArray(new long[] {value}, true);
    }

    public static UnsignedLongArray fromLimbs(long[] limbs) {
        Objects.requireNonNull(limbs, "limbs");
        return limbs.length == 0 ? ZERO : new UnsignedLongArray(limbs.clone(), false);
    }

    public static UnsignedLongArray fromDecimal(String value) {
        Objects.requireNonNull(value, "value");
        if (value.isEmpty()) {
            throw new NumberFormatException("Empty unsigned integer");
        }

        int firstChunkLength = value.length() % 9;
        if (firstChunkLength == 0) {
            firstChunkLength = 9;
        }
        UnsignedLongArray result = fromLong(parseDecimalChunk(value, 0, firstChunkLength));
        for (int offset = firstChunkLength; offset < value.length(); offset += 9) {
            result = result.multiplyAddSmall(DECIMAL_BASE, parseDecimalChunk(value, offset, 9));
        }
        return result;
    }

    public long[] toLongArray() {
        return limbs.clone();
    }

    public int limbCount() {
        return limbs.length;
    }

    public boolean isZero() {
        return limbs.length == 0;
    }

    public UnsignedLongArray add(long value) {
        if (value < 0) {
            throw new IllegalArgumentException("Value must be non-negative: " + value);
        }
        if (value == 0) {
            return this;
        }
        if (limbs.length == 0) {
            return fromLong(value);
        }
        if (limbs.length == 1) {
            long sum = limbs[0] + value;
            if (Long.compareUnsigned(sum, limbs[0]) < 0) {
                return new UnsignedLongArray(new long[] {sum, 1}, true);
            }
            return new UnsignedLongArray(new long[] {sum}, true);
        }

        long[] result = limbs.clone();
        long sum = result[0] + value;
        result[0] = sum;
        if (Long.compareUnsigned(sum, limbs[0]) >= 0) {
            return new UnsignedLongArray(result, true);
        }

        int index = 1;
        while (index < result.length && result[index] == -1L) {
            result[index++] = 0;
        }
        if (index == result.length) {
            result = Arrays.copyOf(result, result.length + 1);
            result[result.length - 1] = 1;
        } else {
            result[index]++;
        }
        return new UnsignedLongArray(result, true);
    }

    public UnsignedLongArray add(UnsignedLongArray other) {
        Objects.requireNonNull(other, "other");
        if (isZero()) {
            return other;
        }
        if (other.isZero()) {
            return this;
        }
        if (other.limbs.length == 1 && other.limbs[0] >= 0) {
            return add(other.limbs[0]);
        }

        int resultLength = Math.max(limbs.length, other.limbs.length);
        long[] result = new long[resultLength];
        long carry = 0;
        for (int i = 0; i < resultLength; i++) {
            long left = i < limbs.length ? limbs[i] : 0;
            long right = i < other.limbs.length ? other.limbs[i] : 0;
            long sum = left + right;
            long carryFromOperands = Long.compareUnsigned(sum, left) < 0 ? 1 : 0;
            long withCarry = sum + carry;
            long carryFromPrevious = Long.compareUnsigned(withCarry, sum) < 0 ? 1 : 0;
            result[i] = withCarry;
            carry = carryFromOperands | carryFromPrevious;
        }
        if (carry == 0) {
            return new UnsignedLongArray(result, true);
        }
        result = Arrays.copyOf(result, resultLength + 1);
        result[resultLength] = carry;
        return new UnsignedLongArray(result, true);
    }

    public UnsignedLongArray subtract(long value) {
        if (value < 0) {
            throw new IllegalArgumentException("Value must be non-negative: " + value);
        }
        if (value == 0) {
            return this;
        }
        if (limbs.length == 0 || limbs.length == 1 && Long.compareUnsigned(limbs[0], value) < 0) {
            throw new ArithmeticException("Unsigned subtraction underflow");
        }
        if (limbs.length == 1) {
            long difference = limbs[0] - value;
            return difference == 0 ? ZERO : new UnsignedLongArray(new long[] {difference}, true);
        }

        int outputLength = limbs.length;
        if (limbs[outputLength - 1] == 1 && lowerPartLessThan(outputLength - 1, value)) {
            outputLength--;
        }
        long[] result = new long[outputLength];
        long borrow = 0;
        for (int i = 0; i < outputLength; i++) {
            long left = limbs[i];
            long right = i == 0 ? value : 0;
            long difference = left - right - borrow;
            boolean borrowOut = borrow == 0
                    ? Long.compareUnsigned(left, right) < 0
                    : Long.compareUnsigned(left, right) <= 0;
            result[i] = difference;
            borrow = borrowOut ? 1 : 0;
        }
        return new UnsignedLongArray(result, true);
    }

    public UnsignedLongArray subtract(UnsignedLongArray other) {
        Objects.requireNonNull(other, "other");
        if (compareTo(other) < 0) {
            throw new ArithmeticException("Unsigned subtraction underflow");
        }
        if (other.isZero()) {
            return this;
        }
        if (other.limbs.length == 1 && other.limbs[0] >= 0) {
            return subtract(other.limbs[0]);
        }

        long[] result = limbs.clone();
        long borrow = 0;
        for (int i = 0; i < result.length; i++) {
            long left = result[i];
            long right = i < other.limbs.length ? other.limbs[i] : 0;
            long difference = left - right - borrow;
            boolean borrowOut = borrow == 0
                    ? Long.compareUnsigned(left, right) < 0
                    : Long.compareUnsigned(left, right) <= 0;
            result[i] = difference;
            borrow = borrowOut ? 1 : 0;
        }
        return new UnsignedLongArray(result, false);
    }

    public long toLongSaturated() {
        if (limbs.length == 0) {
            return 0;
        }
        if (limbs.length > 1 || limbs[0] < 0) {
            return Long.MAX_VALUE;
        }
        return limbs[0];
    }

    private UnsignedLongArray multiplyAddSmall(long multiplier, long addend) {
        if (isZero()) {
            return addend == 0 ? ZERO : fromLong(addend);
        }

        long carry = addend;
        for (long limb : limbs) {
            long low = limb * multiplier;
            long high = unsignedMultiplyHigh(limb, multiplier);
            long withCarry = low + carry;
            if (Long.compareUnsigned(withCarry, low) < 0) {
                high++;
            }
            carry = high;
        }

        int outputLength = limbs.length + (carry == 0 ? 0 : 1);
        long[] result = new long[outputLength];
        carry = addend;
        for (int i = 0; i < limbs.length; i++) {
            long limb = limbs[i];
            long low = limb * multiplier;
            long high = unsignedMultiplyHigh(limb, multiplier);
            long withCarry = low + carry;
            if (Long.compareUnsigned(withCarry, low) < 0) {
                high++;
            }
            result[i] = withCarry;
            carry = high;
        }
        if (carry != 0) {
            result[limbs.length] = carry;
        }
        return new UnsignedLongArray(result, true);
    }

    private static long unsignedMultiplyHigh(long value, long multiplier) {
        long lowPart = value & 0xFFFF_FFFFL;
        long highPart = value >>> 32;
        long lowProduct = lowPart * multiplier;
        long highProduct = highPart * multiplier;
        long low = lowProduct + (highProduct << 32);
        long carry = Long.compareUnsigned(low, lowProduct) < 0 ? 1 : 0;
        return (highProduct >>> 32) + carry;
    }

    private boolean lowerPartLessThan(int lowerLength, long value) {
        for (int i = lowerLength - 1; i >= 1; i--) {
            if (limbs[i] != 0) {
                return false;
            }
        }
        return Long.compareUnsigned(limbs[0], value) < 0;
    }

    private static int parseDecimalChunk(String value, int offset, int length) {
        int result = 0;
        int end = offset + length;
        for (int i = offset; i < end; i++) {
            char digit = value.charAt(i);
            if (digit < '0' || digit > '9') {
                throw new NumberFormatException("Invalid unsigned integer: " + value);
            }
            result = result * 10 + digit - '0';
        }
        return result;
    }

    private static long[] normalize(long[] source) {
        int length = source.length;
        while (length > 0 && source[length - 1] == 0) {
            length--;
        }
        return length == source.length ? source : Arrays.copyOf(source, length);
    }

    @Override
    public int compareTo(UnsignedLongArray other) {
        int lengthComparison = Integer.compare(limbs.length, other.limbs.length);
        if (lengthComparison != 0) {
            return lengthComparison;
        }
        for (int i = limbs.length - 1; i >= 0; i--) {
            int limbComparison = Long.compareUnsigned(limbs[i], other.limbs[i]);
            if (limbComparison != 0) {
                return limbComparison;
            }
        }
        return 0;
    }

    public int compareTo(long value) {
        if (value < 0) {
            throw new IllegalArgumentException("Value must be non-negative: " + value);
        }
        if (limbs.length == 0) {
            return value == 0 ? 0 : -1;
        }
        if (limbs.length > 1) {
            return 1;
        }
        return Long.compareUnsigned(limbs[0], value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
                || obj instanceof UnsignedLongArray other && Arrays.equals(limbs, other.limbs);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(limbs);
    }

    @Override
    public String toString() {
        if (isZero()) {
            return "0";
        }

        long[] current = limbs.clone();
        int currentLength = current.length;
        int[] chunks = new int[Math.max(1, limbs.length * 2)];
        int chunkCount = 0;
        while (currentLength > 0) {
            long[] quotient = new long[currentLength];
            long remainder = 0;
            for (int i = currentLength - 1; i >= 0; i--) {
                long limb = current[i];
                long high = (remainder << 32) | (limb >>> 32);
                long quotientHigh = high / DECIMAL_BASE;
                remainder = high % DECIMAL_BASE;
                long low = (remainder << 32) | (limb & LOW_INT_MASK);
                long quotientLow = low / DECIMAL_BASE;
                remainder = low % DECIMAL_BASE;
                quotient[i] = (quotientHigh << 32) | quotientLow;
            }
            if (chunkCount == chunks.length) {
                chunks = Arrays.copyOf(chunks, chunks.length * 2);
            }
            chunks[chunkCount++] = (int) remainder;
            while (currentLength > 0 && quotient[currentLength - 1] == 0) {
                currentLength--;
            }
            current = quotient;
        }

        StringBuilder result = new StringBuilder(chunkCount * 9);
        result.append(chunks[chunkCount - 1]);
        for (int i = chunkCount - 2; i >= 0; i--) {
            appendPaddedChunk(result, chunks[i]);
        }
        return result.toString();
    }

    private static void appendPaddedChunk(StringBuilder result, int chunk) {
        int divisor = 100_000_000;
        while (divisor > 0) {
            result.append((char) ('0' + chunk / divisor));
            chunk %= divisor;
            divisor /= 10;
        }
    }
}
