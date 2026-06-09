package cn.dancingsnow.bigger_ae2.init;

import appeng.block.crafting.CraftingUnitBlock;
import appeng.core.definitions.AEBlocks;
import cn.dancingsnow.bigger_ae2.block.ModCraftingUnitType;
import cn.dancingsnow.bigger_ae2.util.ModelUtil;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;

import static cn.dancingsnow.bigger_ae2.BiggerAE2Mod.REGISTRATE;

@SuppressWarnings("CodeBlock2Expr")
public class ModBlocks {

    public static final BlockEntry<CraftingUnitBlock> ACCELERATOR_4 = REGISTRATE
        .block("4_core_crafting_accelerator", p -> new CraftingUnitBlock(p, ModCraftingUnitType.ACCELERATOR_4))
        .blockstate(ModelUtil.craftingUnitModel(ModCraftingUnitType.ACCELERATOR_4))
        .recipe((ctx, provider) -> {
            ShapelessRecipeBuilder.shapeless(provider.itemLookup(), RecipeCategory.MISC, ctx.get())
                .requires(AEBlocks.CRAFTING_ACCELERATOR)
                .requires(AEBlocks.CRAFTING_ACCELERATOR)
                .requires(AEBlocks.CRAFTING_ACCELERATOR)
                .requires(AEBlocks.CRAFTING_ACCELERATOR)
                .unlockedBy("has_item", provider.has(AEBlocks.CRAFTING_ACCELERATOR))
                .save(provider);
        })
        .defaultLoot()
        .simpleItem()
        .register();

    public static final BlockEntry<CraftingUnitBlock> ACCELERATOR_16 = REGISTRATE
        .block("16_core_crafting_accelerator", p -> new CraftingUnitBlock(p, ModCraftingUnitType.ACCELERATOR_16))
        .blockstate(ModelUtil.craftingUnitModel(ModCraftingUnitType.ACCELERATOR_16))
        .recipe((ctx, provider) -> {
            ShapelessRecipeBuilder.shapeless(provider.itemLookup(), RecipeCategory.MISC, ctx.get())
                .requires(ACCELERATOR_4)
                .requires(ACCELERATOR_4)
                .requires(ACCELERATOR_4)
                .requires(ACCELERATOR_4)
                .unlockedBy("has_item", provider.has(ACCELERATOR_4))
                .save(provider);
        })
        .defaultLoot()
        .simpleItem()
        .register();

    public static final BlockEntry<CraftingUnitBlock> ACCELERATOR_64 = REGISTRATE
        .block("64_core_crafting_accelerator", p -> new CraftingUnitBlock(p, ModCraftingUnitType.ACCELERATOR_64))
        .blockstate(ModelUtil.craftingUnitModel(ModCraftingUnitType.ACCELERATOR_64))
        .recipe((ctx, provider) -> {
            ShapelessRecipeBuilder.shapeless(provider.itemLookup(), RecipeCategory.MISC, ctx.get())
                .requires(ACCELERATOR_16)
                .requires(ACCELERATOR_16)
                .requires(ACCELERATOR_16)
                .requires(ACCELERATOR_16)
                .unlockedBy("has_item", provider.has(ACCELERATOR_16))
                .save(provider);
        })
        .defaultLoot()
        .simpleItem()
        .register();

    public static final BlockEntry<CraftingUnitBlock> ACCELERATOR_256 = REGISTRATE
        .block("256_core_crafting_accelerator", p -> new CraftingUnitBlock(p, ModCraftingUnitType.ACCELERATOR_256))
        .blockstate(ModelUtil.craftingUnitModel(ModCraftingUnitType.ACCELERATOR_256))
        .recipe((ctx, provider) -> {
            ShapelessRecipeBuilder.shapeless(provider.itemLookup(), RecipeCategory.MISC, ctx.get())
                .requires(ACCELERATOR_64)
                .requires(ACCELERATOR_64)
                .requires(ACCELERATOR_64)
                .requires(ACCELERATOR_64)
                .unlockedBy("has_item", provider.has(ACCELERATOR_64))
                .save(provider);
        })
        .defaultLoot()
        .simpleItem()
        .register();

    public static final BlockEntry<CraftingUnitBlock> ACCELERATOR_1024 = REGISTRATE
        .block("1024_core_crafting_accelerator", p -> new CraftingUnitBlock(p, ModCraftingUnitType.ACCELERATOR_1024))
        .blockstate(ModelUtil.craftingUnitModel(ModCraftingUnitType.ACCELERATOR_1024))
        .recipe((ctx, provider) -> {
            ShapelessRecipeBuilder.shapeless(provider.itemLookup(), RecipeCategory.MISC, ctx.get())
                .requires(ACCELERATOR_256)
                .requires(ACCELERATOR_256)
                .requires(ACCELERATOR_256)
                .requires(ACCELERATOR_256)
                .unlockedBy("has_item", provider.has(ACCELERATOR_256))
                .save(provider);
        })
        .defaultLoot()
        .simpleItem()
        .register();

    public static void register() {
    }
}
