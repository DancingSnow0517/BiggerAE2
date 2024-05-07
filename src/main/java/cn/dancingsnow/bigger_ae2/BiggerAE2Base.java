package cn.dancingsnow.bigger_ae2;

import appeng.api.storage.StorageCells;
import appeng.api.upgrades.Upgrades;
import appeng.core.definitions.AEItems;
import appeng.core.localization.GuiText;
import cn.dancingsnow.bigger_ae2.data.generator.BiggerAE2Datagen;
import cn.dancingsnow.bigger_ae2.init.ModBlockEntities;
import cn.dancingsnow.bigger_ae2.init.ModBlocks;
import cn.dancingsnow.bigger_ae2.init.ModCreativeTab;
import cn.dancingsnow.bigger_ae2.init.ModItems;
import cn.dancingsnow.bigger_ae2.item.cell.DigitalSingularityCellItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class BiggerAE2Base {

    public BiggerAE2Base() {
        BiggerAE2Mod.LOGGER.info("Base");

        ModCreativeTab.register();
        ModItems.register();
        ModBlocks.register();
        ModBlockEntities.register();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(BiggerAE2Base::initUpgrades);
        modEventBus.addListener(BiggerAE2Base::initStorageCells);

        BiggerAE2Datagen.init();
    }

    private static void initUpgrades(FMLCommonSetupEvent event) {
        var storageCellGroup = GuiText.StorageCells.getTranslationKey();

        Upgrades.add(AEItems.VOID_CARD, ModItems.QUANTUM_ITEM_CELL, 1, storageCellGroup);
        Upgrades.add(AEItems.VOID_CARD, ModItems.SINGULARITY_ITEM_CELL, 1, storageCellGroup);
        Upgrades.add(AEItems.VOID_CARD, ModItems.QUANTUM_FLUID_CELL, 1, storageCellGroup);
        Upgrades.add(AEItems.VOID_CARD, ModItems.SINGULARITY_FLUID_CELL, 1, storageCellGroup);
    }

    private static void initStorageCells(FMLCommonSetupEvent event) {
        StorageCells.addCellHandler(DigitalSingularityCellItem.HANDLER);
    }
}
