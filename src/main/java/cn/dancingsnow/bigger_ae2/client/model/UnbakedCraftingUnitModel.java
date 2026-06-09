package cn.dancingsnow.bigger_ae2.client.model;

import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;
import cn.dancingsnow.bigger_ae2.block.ModCraftingUnitType;
import cn.dancingsnow.bigger_ae2.client.ModCraftingUnitModelProvider;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import org.jspecify.annotations.NonNull;

public record UnbakedCraftingUnitModel(ModCraftingUnitType type) implements CustomUnbakedBlockStateModel {

    public static final Identifier ID = BiggerAE2Mod.of("crafting_cube");
    public static final MapCodec<UnbakedCraftingUnitModel> MAP_CODEC = RecordCodecBuilder
        .mapCodec(instance -> instance.group(
            ModCraftingUnitType.CODEC.fieldOf("unit_type").forGetter(UnbakedCraftingUnitModel::type)
        ).apply(instance, UnbakedCraftingUnitModel::new));

    @Override
    public MapCodec<? extends CustomUnbakedBlockStateModel> codec() {
        return MAP_CODEC;
    }

    @Override
    public @NonNull BlockStateModel bake(ModelBaker modelBaker) {
        ModCraftingUnitModelProvider provider = new ModCraftingUnitModelProvider(type);
        return provider.bake(modelBaker.materials());
    }

    @Override
    public void resolveDependencies(@NonNull Resolver resolver) {

    }
}
