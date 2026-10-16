package cn.dancingsnow.bigger_ae2.item.cell;

import appeng.api.stacks.AEKey;
import cn.dancingsnow.bigger_ae2.util.UnsignedLongArray;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record DigitalSingularityStorage(@Nullable AEKey storedItem, UnsignedLongArray count) {
    public static final Codec<DigitalSingularityStorage> CODEC =
        RecordCodecBuilder.create(instance -> instance
            .group(
                AEKey.CODEC.fieldOf("key").forGetter(DigitalSingularityStorage::storedItem),
                UnsignedLongArray.CODEC.fieldOf("count").forGetter(DigitalSingularityStorage::count))
            .apply(instance, DigitalSingularityStorage::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, DigitalSingularityStorage> STREAM_CODEC = StreamCodec.composite(
        AEKey.OPTIONAL_STREAM_CODEC,
        DigitalSingularityStorage::storedItem,
        UnsignedLongArray.STREAM_CODEC,
        DigitalSingularityStorage::count,
        DigitalSingularityStorage::new
    );

    public DigitalSingularityStorage {
        Objects.requireNonNull(count, "count");
    }
}
