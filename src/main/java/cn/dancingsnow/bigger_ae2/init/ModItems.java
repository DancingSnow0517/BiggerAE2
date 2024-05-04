package cn.dancingsnow.bigger_ae2.init;

import appeng.api.stacks.AEKeyType;
import appeng.core.definitions.AEItems;
import appeng.core.definitions.ItemDefinition;
import appeng.items.materials.StorageComponentItem;
import appeng.items.storage.BasicStorageCell;
import appeng.items.storage.StorageTier;
import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;
import cn.dancingsnow.bigger_ae2.util.FormattingUtil;
import lombok.Getter;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ModItems {
    @Getter
    private static final List<ItemDefinition<?>> ITEMS = new ArrayList<>();

    public static final ItemDefinition<StorageComponentItem> CELL_COMPONENT_SINGULARITY = item(
        "Digital Singularity Storage Component",
        "cell_component_digital_singularity",
        p -> new StorageComponentItem(p, 1 << 31 - 1)
    );

    public static final ItemDefinition<StorageComponentItem> CELL_COMPONENT_QUANTUM = item(
        "Quantum Storage Component",
        "cell_component_quantum",
        p -> new StorageComponentItem(p, 1 << 28 - 1)
    );

    public static final StorageTier SINGULARITY_TIRE = new StorageTier(
        99,
        "digital_singularity",
        1 << 31 - 1,
        25,
        CELL_COMPONENT_SINGULARITY::asItem
    );

    public static final StorageTier QUANTUM_TIRE = new StorageTier(
        99,
        "quantum",
        1 << 28 - 1,
        20,
        CELL_COMPONENT_QUANTUM::asItem
    );

    public static final ItemDefinition<BasicStorageCell> ITEM_CELL_SINGULARITY = itemCell(SINGULARITY_TIRE);
    public static final ItemDefinition<BasicStorageCell> ITEM_CELL_QUANTUM = itemCell(QUANTUM_TIRE);

    public static final ItemDefinition<BasicStorageCell> FLUID_CELL_SINGULARITY = fluidCell(SINGULARITY_TIRE);
    public static final ItemDefinition<BasicStorageCell> FLUID_CELL_QUANTUM = fluidCell(QUANTUM_TIRE);

    public static <T extends Item> ItemDefinition<T> item(
        String englishName, String id, Function<Item.Properties, T> factory) {
        var definition = new ItemDefinition<>(englishName, BiggerAE2Mod.of(id), factory.apply(new Item.Properties()));
        ITEMS.add(definition);
        return definition;
    }

    private static ItemDefinition<BasicStorageCell> itemCell(StorageTier tier) {
        return item(
            FormattingUtil.toEnglishName(tier.namePrefix()) + "Item Storage Cell",
            "item_storage_cell_" + tier.namePrefix(),
            p -> new BasicStorageCell(
                p.stacksTo(1),
                tier.componentSupplier().get(),
                AEItems.ITEM_CELL_HOUSING,
                tier.idleDrain(),
                tier.bytes() / 1024,
                65536,
                1,
                AEKeyType.items()
            )
        );
    }

    private static ItemDefinition<BasicStorageCell> fluidCell(StorageTier tier) {
        return item(
            FormattingUtil.toEnglishName(tier.namePrefix()) + "Fluid Storage Cell",
            "fluid_storage_cell_" + tier.namePrefix(),
            p -> new BasicStorageCell(
                p.stacksTo(1),
                tier.componentSupplier().get(),
                AEItems.ITEM_CELL_HOUSING,
                tier.idleDrain(),
                tier.bytes() / 1024,
                65536,
                1,
                AEKeyType.fluids()
            )
        );
    }
}
