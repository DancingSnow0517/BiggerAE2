package cn.dancingsnow.bigger_ae2.integration.appliedflux;

import cn.dancingsnow.bigger_ae2.init.ModItems;
import cn.dancingsnow.bigger_ae2.integration.appliedflux.item.AdvancedItemFECell;
import cn.dancingsnow.bigger_ae2.item.cell.DigitalSingularityCellItem;

import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import appeng.items.materials.MaterialItem;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;

import com.glodblock.github.appflux.common.AFSingletons;
import com.glodblock.github.appflux.common.me.key.type.FluxKeyType;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;

import static cn.dancingsnow.bigger_ae2.BiggerAE2Mod.REGISTRATE;

public class AppliedFluxItems {

    public static final ItemEntry<MaterialItem> ADVANCED_FLUX_CELL_HOUSING = REGISTRATE
            .item("advanced_flux_cell_housing", MaterialItem::new)
            .recipe((ctx, provider) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ctx.get())
                    .pattern("ABA")
                    .pattern("B B")
                    .pattern("CCC")
                    .define('A', AEBlocks.QUARTZ_GLASS)
                    .define('B', AEItems.SKY_DUST)
                    .define('C', AFSingletons.HARDEN_INSULATING_RESIN)
                    .unlockedBy(
                            "has_item", RegistrateRecipeProvider.has(AFSingletons.HARDEN_INSULATING_RESIN))
                    .save(provider))
            .register();

    public static final ItemEntry<AdvancedItemFECell> QUANTUM_FLUX_CELL = REGISTRATE
            .item(
                    "quantum_flux_storage_cell",
                    p -> new AdvancedItemFECell(ModItems.QUANTUM_CELL_COMPONENT, (1 << 28 - 1) / 1024, 20))
            .recipe((ctx, provider) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get())
                    .requires(ADVANCED_FLUX_CELL_HOUSING)
                    .requires(ModItems.QUANTUM_CELL_COMPONENT)
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(ModItems.QUANTUM_CELL_COMPONENT))
                    .save(provider))
            .register();

    public static final ItemEntry<DigitalSingularityCellItem> SINGULARITY_FLUX_CELL = REGISTRATE
            .item(
                    "digital_singularity_flux_storage_cell",
                    p -> new DigitalSingularityCellItem(
                            p, FluxKeyType.TYPE, ModItems.SINGULARITY_CELL_COMPONENT, ADVANCED_FLUX_CELL_HOUSING))
            .recipe((ctx, provider) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get())
                    .requires(ADVANCED_FLUX_CELL_HOUSING)
                    .requires(ModItems.SINGULARITY_CELL_COMPONENT)
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(ModItems.QUANTUM_CELL_COMPONENT))
                    .save(provider))
            .register();

    public static void register() {}
}
