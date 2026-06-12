package cn.dancingsnow.bigger_ae2.util;

import appeng.block.crafting.AbstractCraftingUnitBlock;
import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;
import cn.dancingsnow.bigger_ae2.block.ModCraftingUnitType;
import cn.dancingsnow.bigger_ae2.client.model.UnbakedCraftingUnitModel;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.generators.RegistrateBlockModelGenerator;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import net.neoforged.neoforge.client.model.generators.blockstate.CustomBlockStateModelBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelUtil {
    public static <B extends Block> NonNullBiConsumer<DataGenContext<Block, B>, RegistrateBlockModelGenerator> craftingUnitModel(ModCraftingUnitType type) {
        return (ctx, prov) -> {
            Identifier unformedModel = ModelTemplates.CUBE_ALL.create(
                prov.modLoc("block/crafting/"+ctx.getName()),
                TextureMapping.cube(makeMaterial("block/crafting/" + ctx.getName())),
                prov.modelOutput
            );
            MultiVariant formedModel = customBlockStateModel(new UnbakedCraftingUnitModel(type));

            prov.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(ctx.get())
                    .with(PropertyDispatch.initial(AbstractCraftingUnitBlock.FORMED)
                        .select(false, BlockModelGenerators.plainVariant(unformedModel))
                        .select(true, formedModel)));

            prov.registerSimpleItemModel(ctx.get(), unformedModel);
        };
    }

    private static Material makeMaterial(String name) {
        return new Material(BiggerAE2Mod.of(name));
    }

    private static MultiVariant customBlockStateModel(CustomUnbakedBlockStateModel model) {
        return MultiVariant.of(new CustomBlockStateModelBuilder.Simple(model));
    }
}
