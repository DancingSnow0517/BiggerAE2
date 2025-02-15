package cn.dancingsnow.bigger_ae2.integration.appliedexperienced;

import cn.dancingsnow.bigger_ae2.init.ModItems;
import cn.dancingsnow.bigger_ae2.integration.appliedexperienced.item.AdvancedExperienceStorageCell;
import cn.dancingsnow.bigger_ae2.item.cell.DigitalSingularityCellItem;

import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import appeng.items.materials.MaterialItem;
import appeng.recipes.game.StorageCellDisassemblyRecipe;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.neoforged.neoforge.common.Tags;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import es.degrassi.appexp.me.key.ExperienceKeyType;

import java.util.List;

import static cn.dancingsnow.bigger_ae2.BiggerAE2Mod.REGISTRATE;

public class AppliedExperiencedItems {
    public static final ItemEntry<MaterialItem> ADVANCED_EXPERIENCE_CELL_HOUSING = REGISTRATE
            .item("advanced_experience_cell_housing", MaterialItem::new)
            .recipe((ctx, provider) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ctx.get())
                    .pattern("ABA")
                    .pattern("B B")
                    .pattern("CCC")
                    .define('A', AEBlocks.QUARTZ_GLASS)
                    .define('B', AEItems.SKY_DUST)
                    .define('C', Tags.Items.GEMS_LAPIS)
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(Tags.Items.GEMS_LAPIS))
                    .save(provider))
            .register();

    public static final ItemEntry<AdvancedExperienceStorageCell> QUANTUM_EXPERIENCE_CELL = REGISTRATE
            .item(
                    "quantum_experience_storage_cell",
                    p -> new AdvancedExperienceStorageCell(p, 20, (1 << 28 - 1) / 1024))
            .recipe((ctx, provider) -> {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get())
                        .requires(ADVANCED_EXPERIENCE_CELL_HOUSING)
                        .requires(ModItems.QUANTUM_CELL_COMPONENT)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(ModItems.QUANTUM_CELL_COMPONENT))
                        .save(provider);
                provider.accept(
                        ctx.getId().withPrefix("cell_upgrade/"),
                        new StorageCellDisassemblyRecipe(
                                ctx.get(),
                                List.of(
                                        ADVANCED_EXPERIENCE_CELL_HOUSING.asStack(),
                                        ModItems.QUANTUM_CELL_COMPONENT.asStack())),
                        null);
            })
            .register();

    public static final ItemEntry<DigitalSingularityCellItem> SINGULARITY_EXPERIENCE_CELL = REGISTRATE
            .item(
                    "digital_singularity_experience_storage_cell",
                    p -> new DigitalSingularityCellItem(
                            p,
                            ExperienceKeyType.TYPE,
                            ModItems.SINGULARITY_CELL_COMPONENT,
                            ADVANCED_EXPERIENCE_CELL_HOUSING))
            .recipe((ctx, provider) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get())
                    .requires(ADVANCED_EXPERIENCE_CELL_HOUSING)
                    .requires(ModItems.SINGULARITY_CELL_COMPONENT)
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(ModItems.SINGULARITY_CELL_COMPONENT))
                    .save(provider))
            .register();

    public static void register() {}
}
