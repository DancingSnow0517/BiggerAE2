package cn.dancingsnow.bigger_ae2.benchmark;

import cn.dancingsnow.bigger_ae2.util.UnsignedLongArray;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class UnsignedLongArrayBenchmark {
    private static final long DELTA = 123_456_789L;
    private static final int CHAIN_LENGTH = 16;

    @Param({"1", "2", "8", "32"})
    private int limbCount;

    @Param({"ordinary", "carry", "borrow"})
    private String pattern;

    private UnsignedLongArray value;
    private UnsignedLongArray other;
    private UnsignedLongArray chainValue;
    private BigInteger bigValue;
    private BigInteger bigOther;
    private BigInteger bigDelta;
    private BigInteger bigChainValue;

    @Setup(Level.Trial)
    public void setup() {
        long[][] operands = createOperands();
        value = UnsignedLongArray.fromLimbs(operands[0]);
        other = UnsignedLongArray.fromLimbs(operands[1]);
        bigValue = new BigInteger(value.toString());
        bigOther = new BigInteger(other.toString());
        bigDelta = BigInteger.valueOf(DELTA);
        bigChainValue = bigValue.add(bigOther.multiply(BigInteger.valueOf(CHAIN_LENGTH)));
        chainValue = UnsignedLongArray.fromDecimal(bigChainValue.toString());
        verifySetup();
    }

    @Benchmark
    public UnsignedLongArray unsignedAddLong() {
        return value.add(DELTA);
    }

    @Benchmark
    public BigInteger bigIntegerAddLong() {
        return bigValue.add(bigDelta);
    }

    @Benchmark
    public UnsignedLongArray unsignedSubtractLong() {
        return value.subtract(DELTA);
    }

    @Benchmark
    public BigInteger bigIntegerSubtractLong() {
        return bigValue.subtract(bigDelta);
    }

    @Benchmark
    public UnsignedLongArray unsignedAdd() {
        return value.add(other);
    }

    @Benchmark
    public BigInteger bigIntegerAdd() {
        return bigValue.add(bigOther);
    }

    @Benchmark
    public UnsignedLongArray unsignedSubtract() {
        return value.subtract(other);
    }

    @Benchmark
    public BigInteger bigIntegerSubtract() {
        return bigValue.subtract(bigOther);
    }

    @Benchmark
    public UnsignedLongArray unsignedAddChain() {
        UnsignedLongArray result = chainValue;
        for (int i = 0; i < CHAIN_LENGTH; i++) {
            result = result.add(other);
        }
        return result;
    }

    @Benchmark
    public BigInteger bigIntegerAddChain() {
        BigInteger result = bigChainValue;
        for (int i = 0; i < CHAIN_LENGTH; i++) {
            result = result.add(bigOther);
        }
        return result;
    }

    @Benchmark
    public UnsignedLongArray unsignedSubtractChain() {
        UnsignedLongArray result = chainValue;
        for (int i = 0; i < CHAIN_LENGTH; i++) {
            result = result.subtract(other);
        }
        return result;
    }

    @Benchmark
    public BigInteger bigIntegerSubtractChain() {
        BigInteger result = bigChainValue;
        for (int i = 0; i < CHAIN_LENGTH; i++) {
            result = result.subtract(bigOther);
        }
        return result;
    }

    private long[][] createOperands() {
        long[] valueLimbs = new long[limbCount];
        long[] otherLimbs = new long[limbCount];
        switch (pattern) {
            case "carry" -> {
                for (int i = 0; i < limbCount; i++) {
                    valueLimbs[i] = -1L;
                }
                otherLimbs[0] = 1;
                otherLimbs[limbCount - 1] = 1;
            }
            case "borrow" -> {
                if (limbCount == 1) {
                    valueLimbs[0] = 9_000_000_000L;
                    otherLimbs[0] = 1;
                } else {
                    valueLimbs[limbCount - 1] = 2;
                    otherLimbs[0] = 1;
                    otherLimbs[limbCount - 1] = 1;
                }
            }
            case "ordinary" -> {
                valueLimbs[0] = 9_000_000_000L;
                otherLimbs[0] = 987_654_321L;
                for (int i = 1; i < limbCount; i++) {
                    valueLimbs[i] = 0x6000_0000_0000_0000L ^ (long) i * 0x1020_3040L;
                    otherLimbs[i] = 0x1000_0000_0000_0000L ^ (long) i * 0x2040_6080L;
                }
            }
            default -> throw new IllegalArgumentException("Unknown benchmark pattern: " + pattern);
        }
        return new long[][] {valueLimbs, otherLimbs};
    }

    private void verifySetup() {
        verify(value, bigValue);
        verify(other, bigOther);
        verify(chainValue, bigChainValue);
        verify(value.add(DELTA), bigValue.add(bigDelta));
        verify(value.subtract(DELTA), bigValue.subtract(bigDelta));
        verify(value.add(other), bigValue.add(bigOther));
        verify(value.subtract(other), bigValue.subtract(bigOther));
        verify(
                unsignedAddChain(), bigChainValue.add(bigOther.multiply(BigInteger.valueOf(CHAIN_LENGTH))));
        verify(
                unsignedSubtractChain(),
                bigChainValue.subtract(bigOther.multiply(BigInteger.valueOf(CHAIN_LENGTH))));
        if (Integer.signum(value.compareTo(other)) != bigValue.compareTo(bigOther)) {
            throw new AssertionError("Unsigned comparison mismatch");
        }
        verifyBoundaryValues();
    }

    private static void verifyBoundaryValues() {
        long[][] boundaries = {
            {}, {0}, {1}, {Long.MAX_VALUE}, {-1}, {0, 1}, {-1, 1}, {Long.MIN_VALUE, Long.MAX_VALUE}
        };
        for (long[] limbs : boundaries) {
            UnsignedLongArray number = UnsignedLongArray.fromLimbs(limbs);
            String decimal = number.toString();
            UnsignedLongArray parsed = UnsignedLongArray.fromDecimal(decimal);
            if (!number.equals(parsed)) {
                throw new AssertionError("Decimal round-trip mismatch: " + decimal);
            }
        }
    }

    private static void verify(UnsignedLongArray actual, BigInteger expected) {
        String actualDecimal = actual.toString();
        if (!actualDecimal.equals(expected.toString())) {
            throw new AssertionError("Unsigned result mismatch: " + actualDecimal + " != " + expected);
        }
    }
}
