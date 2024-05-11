package cn.dancingsnow.bigger_ae2.integration.appliedflux;

import appeng.items.materials.MaterialItem;
import cn.dancingsnow.bigger_ae2.init.ModItems;
import cn.dancingsnow.bigger_ae2.integration.appliedflux.item.AdvancedItemFECell;
import cn.dancingsnow.bigger_ae2.item.cell.DigitalSingularityCellItem;
import com.glodblock.github.appflux.common.me.key.type.FluxKeyType;
import com.tterrag.registrate.util.entry.ItemEntry;

import static cn.dancingsnow.bigger_ae2.BiggerAE2Mod.REGISTRATE;

public class AppliedFluxItems {

    public static final ItemEntry<MaterialItem> ADVANCED_FLUX_CELL_HOUSING = REGISTRATE
        .item("advanced_flux_cell_housing", MaterialItem::new)
        .register();

    public static final ItemEntry<AdvancedItemFECell> QUANTUM_FLUX_CELL = REGISTRATE
        .item("quantum_flux_storage_cell", p -> new AdvancedItemFECell(
            ModItems.QUANTUM_CELL_COMPONENT,
            (1 << 28 - 1) / 1024,
            20
        ))
        .register();

    public static final ItemEntry<DigitalSingularityCellItem> SINGULARITY_FLUX_CELL = REGISTRATE
        .item("digital_singularity_flux_storage_cell", p -> new DigitalSingularityCellItem(
            p,
            FluxKeyType.TYPE,
            ModItems.SINGULARITY_CELL_COMPONENT,
            ADVANCED_FLUX_CELL_HOUSING
        ))
        .register();

    public static void register() {

    }
}
