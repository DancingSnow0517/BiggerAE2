package cn.dancingsnow.bigger_ae2.init;

import appeng.api.stacks.AEKeyType;
import appeng.core.definitions.AEItems;
import appeng.items.materials.StorageComponentItem;
import appeng.items.storage.BasicStorageCell;
import cn.dancingsnow.bigger_ae2.item.cell.DigitalSingularityCellItem;
import com.tterrag.registrate.util.entry.ItemEntry;

import static cn.dancingsnow.bigger_ae2.BiggerAE2Mod.REGISTRATE;

public class ModItems {
    public static final ItemEntry<StorageComponentItem> QUANTUM_CELL_COMPONENT = REGISTRATE
        .item("quantum_cell_component", p -> new StorageComponentItem(p, 1 << 28 - 1))
        .register();

    public static final ItemEntry<BasicStorageCell> QUANTUM_ITEM_CELL = REGISTRATE
        .item("quantum_item_storage_cell", p -> new BasicStorageCell(
            p.stacksTo(1),
            QUANTUM_CELL_COMPONENT,
            AEItems.ITEM_CELL_HOUSING,
            20,
            (1 << 28 - 1) / 1024,
            65536,
            1,
            AEKeyType.items()
        ))
        .register();

    public static final ItemEntry<BasicStorageCell> QUANTUM_FLUID_CELL = REGISTRATE
        .item("quantum_fluid_storage_cell", p -> new BasicStorageCell(
            p.stacksTo(1),
            QUANTUM_CELL_COMPONENT,
            AEItems.ITEM_CELL_HOUSING,
            20,
            (1 << 28 - 1) / 1024,
            65536,
            1,
            AEKeyType.fluids()
        ))
        .register();

    public static final ItemEntry<DigitalSingularityCellItem> SINGULARITY_ITEM_CELL = REGISTRATE
        .item("digital_singularity_item_storage_cell", p -> new DigitalSingularityCellItem(p, AEKeyType.items()))
        .register();

    public static final ItemEntry<DigitalSingularityCellItem> SINGULARITY_FLUID_CELL = REGISTRATE
        .item("digital_singularity_fluid_storage_cell", p -> new DigitalSingularityCellItem(p, AEKeyType.fluids()))
        .register();

    public static void register() {

    }
}
