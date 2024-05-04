package cn.dancingsnow.bigger_ae2;


import appeng.api.storage.StorageCells;
import appeng.api.upgrades.Upgrades;
import appeng.core.definitions.AEItems;
import appeng.core.localization.GuiText;
import cn.dancingsnow.bigger_ae2.data.generator.BiggerAE2Datagen;
import cn.dancingsnow.bigger_ae2.init.ModCreativeTab;
import cn.dancingsnow.bigger_ae2.init.ModItems;
import cn.dancingsnow.bigger_ae2.item.cell.DigitalSingularityCellItem;
import com.mojang.logging.LogUtils;

import com.tterrag.registrate.Registrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(BiggerAE2Mod.MOD_ID)
public class BiggerAE2Mod {

    public static final String MOD_ID = "bigger_ae2";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Registrate REGISTRATE = Registrate.create(MOD_ID);


    public BiggerAE2Mod() {
        ModItems.register();
        ModCreativeTab.register();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(BiggerAE2Mod::initUpgrades);
        modEventBus.addListener(BiggerAE2Mod::initStorageCells);

        BiggerAE2Datagen.init();
    }

    public static ResourceLocation of(String path) {
        return new ResourceLocation(MOD_ID, path);
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
