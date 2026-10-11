package cn.dancingsnow.bigger_ae2.item.cell;

import cn.dancingsnow.bigger_ae2.util.UnsignedLongArray;

import appeng.api.stacks.AEKey;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

@Getter
public class DigitalSingularityStorage {
    private static final Codec<UnsignedLongArray> COUNT_CODEC = Codec.either(
                    Codec.STRING, Codec.LONG.listOf())
            .flatXmap(DigitalSingularityStorage::decodeCount, DigitalSingularityStorage::encodeCount);

    public static final Codec<DigitalSingularityStorage> CODEC =
            RecordCodecBuilder.create(instance -> instance
                    .group(
                            AEKey.CODEC.fieldOf("key").forGetter(DigitalSingularityStorage::getStoredItem),
                            COUNT_CODEC.fieldOf("count").forGetter(DigitalSingularityStorage::getCount))
                    .apply(instance, DigitalSingularityStorage::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, DigitalSingularityStorage> STREAM_CODEC =
            StreamCodec.ofMember(DigitalSingularityStorage::encode, DigitalSingularityStorage::new);

    @Nullable private final AEKey storedItem;

    private final UnsignedLongArray count;

    public DigitalSingularityStorage(@Nullable AEKey storedItem, UnsignedLongArray count) {
        this.storedItem = storedItem;
        this.count = Objects.requireNonNull(count, "count");
    }

    public DigitalSingularityStorage(RegistryFriendlyByteBuf buf) {
        storedItem = AEKey.readOptionalKey(buf);
        int limbCount = buf.readVarInt();
        if (limbCount < 0 || limbCount > UnsignedLongArray.MAX_LIMBS) {
            throw new IllegalArgumentException("Invalid unsigned integer limb count: " + limbCount);
        }
        long[] limbs = new long[limbCount];
        for (int i = 0; i < limbCount; i++) {
            limbs[i] = buf.readLong();
        }
        count = UnsignedLongArray.fromLimbs(limbs);
    }

    public void encode(RegistryFriendlyByteBuf buf) {
        AEKey.writeOptionalKey(buf, storedItem);
        long[] limbs = count.toLongArray();
        buf.writeVarInt(limbs.length);
        for (long limb : limbs) {
            buf.writeLong(limb);
        }
    }

    private static DataResult<UnsignedLongArray> decodeCount(Either<String, List<Long>> encoded) {
        return encoded.map(
                value -> {
                    try {
                        return DataResult.success(UnsignedLongArray.fromDecimal(value));
                    } catch (NumberFormatException e) {
                        return DataResult.error(() -> "Invalid unsigned integer: " + value);
                    }
                },
                values -> {
                    if (values.size() > UnsignedLongArray.MAX_LIMBS) {
                        return DataResult.error(() -> "Too many unsigned integer limbs: " + values.size());
                    }
                    long[] limbs = new long[values.size()];
                    for (int i = 0; i < values.size(); i++) {
                        limbs[i] = values.get(i);
                    }
                    return DataResult.success(UnsignedLongArray.fromLimbs(limbs));
                });
    }

    private static DataResult<Either<String, List<Long>>> encodeCount(UnsignedLongArray value) {
        long[] limbs = value.toLongArray();
        List<Long> encoded = new java.util.ArrayList<>(limbs.length);
        for (long limb : limbs) {
            encoded.add(limb);
        }
        return DataResult.success(Either.right(encoded));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof DigitalSingularityStorage storage) {
            return Objects.equals(storage.getStoredItem(), getStoredItem())
                    && Objects.equals(storage.getCount(), getCount());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStoredItem(), getCount());
    }
}
