package cn.dancingsnow.bigger_ae2.init;

import appeng.block.crafting.AbstractCraftingUnitBlock;
import appeng.block.crafting.CraftingUnitBlock;
import appeng.core.definitions.AEBlocks;
import cn.dancingsnow.bigger_ae2.block.ModCraftingUnitType;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import static cn.dancingsnow.bigger_ae2.BiggerAE2Mod.REGISTRATE;

public class ModBlocks {

    public static final BlockEntry<CraftingUnitBlock> ACCELERATOR_4 = REGISTRATE
        .block("4_core_crafting_accelerator", p -> new CraftingUnitBlock(ModCraftingUnitType.ACCELERATOR_4))
        .blockstate((ctx, provider) -> {
            String formed = "block/crafting/" + ctx.getName() + "_formed";
            String unformed = "block/crafting/" + ctx.getName();
            provider.models().cubeAll(unformed, provider.modLoc("block/crafting/" + ctx.getName()));
            provider.models().getBuilder(formed);
            provider.getVariantBuilder(ctx.get())
                .forAllStatesExcept(state -> {
                    boolean b = state.getValue(AbstractCraftingUnitBlock.FORMED);
                    return ConfiguredModel.builder()
                        .modelFile(provider.models().getExistingFile(provider.modLoc(b ? formed : unformed)))
                        .build();
                }, AbstractCraftingUnitBlock.POWERED);
        })
        .recipe((ctx, provider) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get())
            .requires(AEBlocks.CRAFTING_ACCELERATOR)
            .requires(AEBlocks.CRAFTING_ACCELERATOR)
            .requires(AEBlocks.CRAFTING_ACCELERATOR)
            .requires(AEBlocks.CRAFTING_ACCELERATOR)
            .unlockedBy("has_item", RegistrateRecipeProvider.has(AEBlocks.CRAFTING_ACCELERATOR))
            .save(provider))
        .defaultLoot()
        .item(BlockItem::new)
        .model((ctx, provider) -> provider.withExistingParent(ctx.getName(), provider.modLoc("block/crafting/" + ctx.getName())))
        .build()
        .register();

    public static final BlockEntry<CraftingUnitBlock> ACCELERATOR_16 = REGISTRATE
        .block("16_core_crafting_accelerator", p -> new CraftingUnitBlock(ModCraftingUnitType.ACCELERATOR_16))
        .blockstate((ctx, provider) -> {
            String formed = "block/crafting/" + ctx.getName() + "_formed";
            String unformed = "block/crafting/" + ctx.getName();
            provider.models().cubeAll(unformed, provider.modLoc("block/crafting/" + ctx.getName()));
            provider.models().getBuilder(formed);
            provider.getVariantBuilder(ctx.get())
                .forAllStatesExcept(state -> {
                    boolean b = state.getValue(AbstractCraftingUnitBlock.FORMED);
                    return ConfiguredModel.builder()
                        .modelFile(provider.models().getExistingFile(provider.modLoc(b ? formed : unformed)))
                        .build();
                }, AbstractCraftingUnitBlock.POWERED);
        })
        .recipe((ctx, provider) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get())
            .requires(ACCELERATOR_4)
            .requires(ACCELERATOR_4)
            .requires(ACCELERATOR_4)
            .requires(ACCELERATOR_4)
            .unlockedBy("has_item", RegistrateRecipeProvider.has(ACCELERATOR_4))
            .save(provider))
        .defaultLoot()
        .item(BlockItem::new)
        .model((ctx, provider) -> provider.withExistingParent(ctx.getName(), provider.modLoc("block/crafting/" + ctx.getName())))
        .build()
        .register();

    public static final BlockEntry<CraftingUnitBlock> ACCELERATOR_64 = REGISTRATE
        .block("64_core_crafting_accelerator", p -> new CraftingUnitBlock(ModCraftingUnitType.ACCELERATOR_64))
        .blockstate((ctx, provider) -> {
            String formed = "block/crafting/" + ctx.getName() + "_formed";
            String unformed = "block/crafting/" + ctx.getName();
            provider.models().cubeAll(unformed, provider.modLoc("block/crafting/" + ctx.getName()));
            provider.models().getBuilder(formed);
            provider.getVariantBuilder(ctx.get())
                .forAllStatesExcept(state -> {
                    boolean b = state.getValue(AbstractCraftingUnitBlock.FORMED);
                    return ConfiguredModel.builder()
                        .modelFile(provider.models().getExistingFile(provider.modLoc(b ? formed : unformed)))
                        .build();
                }, AbstractCraftingUnitBlock.POWERED);
        })
        .recipe((ctx, provider) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get())
            .requires(ACCELERATOR_16)
            .requires(ACCELERATOR_16)
            .requires(ACCELERATOR_16)
            .requires(ACCELERATOR_16)
            .unlockedBy("has_item", RegistrateRecipeProvider.has(ACCELERATOR_16))
            .save(provider))
        .defaultLoot()
        .item(BlockItem::new)
        .model((ctx, provider) -> provider.withExistingParent(ctx.getName(), provider.modLoc("block/crafting/" + ctx.getName())))
        .build()
        .register();

    public static final BlockEntry<CraftingUnitBlock> ACCELERATOR_256 = REGISTRATE
        .block("256_core_crafting_accelerator", p -> new CraftingUnitBlock(ModCraftingUnitType.ACCELERATOR_256))
        .blockstate((ctx, provider) -> {
            String formed = "block/crafting/" + ctx.getName() + "_formed";
            String unformed = "block/crafting/" + ctx.getName();
            provider.models().cubeAll(unformed, provider.modLoc("block/crafting/" + ctx.getName()));
            provider.models().getBuilder(formed);
            provider.getVariantBuilder(ctx.get())
                .forAllStatesExcept(state -> {
                    boolean b = state.getValue(AbstractCraftingUnitBlock.FORMED);
                    return ConfiguredModel.builder()
                        .modelFile(provider.models().getExistingFile(provider.modLoc(b ? formed : unformed)))
                        .build();
                }, AbstractCraftingUnitBlock.POWERED);
        })
        .recipe((ctx, provider) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get())
            .requires(ACCELERATOR_64)
            .requires(ACCELERATOR_64)
            .requires(ACCELERATOR_64)
            .requires(ACCELERATOR_64)
            .unlockedBy("has_item", RegistrateRecipeProvider.has(ACCELERATOR_64))
            .save(provider))
        .defaultLoot()
        .item(BlockItem::new)
        .model((ctx, provider) -> provider.withExistingParent(ctx.getName(), provider.modLoc("block/crafting/" + ctx.getName())))
        .build()
        .register();

    public static final BlockEntry<CraftingUnitBlock> ACCELERATOR_1024 = REGISTRATE
        .block("1024_core_crafting_accelerator", p -> new CraftingUnitBlock(ModCraftingUnitType.ACCELERATOR_1024))
        .blockstate((ctx, provider) -> {
            String formed = "block/crafting/" + ctx.getName() + "_formed";
            String unformed = "block/crafting/" + ctx.getName();
            provider.models().cubeAll(unformed, provider.modLoc("block/crafting/" + ctx.getName()));
            provider.models().getBuilder(formed);
            provider.getVariantBuilder(ctx.get())
                .forAllStatesExcept(state -> {
                    boolean b = state.getValue(AbstractCraftingUnitBlock.FORMED);
                    return ConfiguredModel.builder()
                        .modelFile(provider.models().getExistingFile(provider.modLoc(b ? formed : unformed)))
                        .build();
                }, AbstractCraftingUnitBlock.POWERED);
        })
        .recipe((ctx, provider) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get())
            .requires(ACCELERATOR_256)
            .requires(ACCELERATOR_256)
            .requires(ACCELERATOR_256)
            .requires(ACCELERATOR_256)
            .unlockedBy("has_item", RegistrateRecipeProvider.has(ACCELERATOR_256))
            .save(provider))
        .defaultLoot()
        .item(BlockItem::new)
        .model((ctx, provider) -> provider.withExistingParent(ctx.getName(), provider.modLoc("block/crafting/" + ctx.getName())))
        .build()
        .register();

    public static void register() {

    }

}
