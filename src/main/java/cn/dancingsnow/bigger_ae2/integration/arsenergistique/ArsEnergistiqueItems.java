package cn.dancingsnow.bigger_ae2.integration.arsenergistique;

import appeng.core.definitions.AEBlocks;
import appeng.items.materials.MaterialItem;
import appeng.recipes.game.StorageCellDisassemblyRecipe;
import cn.dancingsnow.bigger_ae2.init.ModItems;
import cn.dancingsnow.bigger_ae2.integration.arsenergistique.item.AdvancedSourceCellItem;
import cn.dancingsnow.bigger_ae2.item.cell.DigitalSingularityCellItem;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import gripe._90.arseng.me.key.SourceKeyType;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.List;

import static cn.dancingsnow.bigger_ae2.BiggerAE2Mod.REGISTRATE;

public class ArsEnergistiqueItems {
    public static final ItemEntry<MaterialItem> ADVANCED_SOURCE_CELL_HOUSING = REGISTRATE
        .item("advanced_source_cell_housing", MaterialItem::new)
        .recipe((ctx, provider) -> {
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ctx.get())
                .pattern("ABA")
                .pattern("B B")
                .pattern("CCC")
                .define('A', AEBlocks.QUARTZ_GLASS)
                .define('B', Items.REDSTONE)
                .define('C', ItemsRegistry.SOURCE_GEM)
                .unlockedBy(
                    "hasitem", RegistrateRecipeProvider.has(ItemsRegistry.SOURCE_GEM))
                .save(provider);
        })
        .register();

    public static final ItemEntry<AdvancedSourceCellItem> QUANTUM_SOURCE_STORAGE_CELL = REGISTRATE
        .item("quantum_source_storage_cell", p -> new AdvancedSourceCellItem(p, 20, (1 << 28 - 1) / 1024))
        .recipe((ctx, provider) -> {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get())
                .requires(ADVANCED_SOURCE_CELL_HOUSING)
                .requires(ModItems.QUANTUM_CELL_COMPONENT)
                .unlockedBy(
                    "has_item", RegistrateRecipeProvider.has(ADVANCED_SOURCE_CELL_HOUSING))
                .save(provider);
            provider.accept(
                ctx.getId().withPrefix("cell_upgrade/"),
                new StorageCellDisassemblyRecipe(
                    ctx.get(),
                    List.of(
                        ADVANCED_SOURCE_CELL_HOUSING.asStack(),
                        ModItems.QUANTUM_CELL_COMPONENT.asStack())),
                null);
        })
        .register();

    public static final ItemEntry<DigitalSingularityCellItem> SINGULARITY_SOURCE_STORAGE_CELL = REGISTRATE
        .item("digital_singularity_source_storage_cell", p -> new DigitalSingularityCellItem(p, SourceKeyType.TYPE, ModItems.SINGULARITY_CELL_COMPONENT, ADVANCED_SOURCE_CELL_HOUSING))
        .recipe((ctx, provider) -> ShapelessRecipeBuilder.shapeless(
                RecipeCategory.MISC, ctx.get())
            .requires(ADVANCED_SOURCE_CELL_HOUSING)
            .requires(ModItems.SINGULARITY_CELL_COMPONENT)
            .unlockedBy("hasitem", RegistrateRecipeProvider.has(ADVANCED_SOURCE_CELL_HOUSING))
            .save(provider))
        .register();

    public static void register() {}
}
