package cn.dancingsnow.bigger_ae2.integration.appliedmekanistics;

import cn.dancingsnow.bigger_ae2.init.ModItems;
import cn.dancingsnow.bigger_ae2.integration.appliedmekanistics.item.AdvancedChemicalStorageCell;
import cn.dancingsnow.bigger_ae2.item.cell.DigitalSingularityCellItem;

import appeng.core.definitions.AEBlocks;
import appeng.items.materials.MaterialItem;
import appeng.recipes.game.StorageCellDisassemblyRecipe;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import me.ramidzkh.mekae2.ae2.MekanismKeyType;
import mekanism.common.tags.MekanismTags;

import java.util.List;

import static cn.dancingsnow.bigger_ae2.BiggerAE2Mod.REGISTRATE;

public class AppliedMekanisticsItems {

    public static final ItemEntry<MaterialItem> ADVANCED_CHEMICAL_CELL_HOUSING = REGISTRATE
            .item("advanced_chemical_cell_housing", MaterialItem::new)
            .recipe((ctx, provider) -> {
                RecipeOutput recipeOutput = provider.withConditions(new ModLoadedCondition("appmek"));
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ctx.get())
                        .pattern("ABA")
                        .pattern("B B")
                        .pattern("CCC")
                        .define('A', AEBlocks.QUARTZ_GLASS)
                        .define('B', Items.REDSTONE)
                        .define('C', MekanismTags.Items.INGOTS_REFINED_OBSIDIAN)
                        .unlockedBy(
                                "hasitem", RegistrateRecipeProvider.has(MekanismTags.Items.INGOTS_REFINED_OBSIDIAN))
                        .save(recipeOutput);
            })
            .register();

    public static final ItemEntry<AdvancedChemicalStorageCell> QUANTUM_CHEMICAL_STORAGE_CELL =
            REGISTRATE
                    .item(
                            "quantum_chemical_storage_cell",
                            p -> new AdvancedChemicalStorageCell(p, 20, (1 << 28 - 1) / 1024, 65536, 1))
                    .recipe((ctx, provider) -> {
                        RecipeOutput recipeOutput = provider.withConditions(new ModLoadedCondition("appmek"));
                        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get())
                                .requires(ADVANCED_CHEMICAL_CELL_HOUSING)
                                .requires(ModItems.QUANTUM_CELL_COMPONENT)
                                .unlockedBy(
                                        "has_item", RegistrateRecipeProvider.has(ADVANCED_CHEMICAL_CELL_HOUSING))
                                .save(recipeOutput);
                        recipeOutput.accept(
                                ctx.getId().withPrefix("cell_upgrade/"),
                                new StorageCellDisassemblyRecipe(
                                        ctx.get(),
                                        List.of(
                                                ADVANCED_CHEMICAL_CELL_HOUSING.asStack(),
                                                ModItems.QUANTUM_CELL_COMPONENT.asStack())),
                                null);
                    })
                    .register();

    public static final ItemEntry<DigitalSingularityCellItem> SINGULARITY_CHEMICAL_STORAGE_CELL =
            REGISTRATE
                    .item(
                            "digital_singularity_chemical_storage_cell",
                            p -> new DigitalSingularityCellItem(
                                    p,
                                    MekanismKeyType.TYPE,
                                    ModItems.SINGULARITY_CELL_COMPONENT,
                                    ADVANCED_CHEMICAL_CELL_HOUSING))
                    .recipe((ctx, provider) -> {
                        RecipeOutput recipeOutput = provider.withConditions(new ModLoadedCondition("appmek"));
                        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get())
                                .requires(ADVANCED_CHEMICAL_CELL_HOUSING)
                                .requires(ModItems.SINGULARITY_CELL_COMPONENT)
                                .unlockedBy("hasitem", RegistrateRecipeProvider.has(ADVANCED_CHEMICAL_CELL_HOUSING))
                                .save(recipeOutput);
                    })
                    .register();

    public static void register() {}
}
