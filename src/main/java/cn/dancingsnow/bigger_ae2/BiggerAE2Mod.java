package cn.dancingsnow.bigger_ae2;

import cn.dancingsnow.bigger_ae2.data.generator.BiggerAE2Datagen;
import cn.dancingsnow.bigger_ae2.init.ModBlockEntities;
import cn.dancingsnow.bigger_ae2.init.ModBlocks;
import cn.dancingsnow.bigger_ae2.init.ModComponents;
import cn.dancingsnow.bigger_ae2.init.ModCreativeTab;
import cn.dancingsnow.bigger_ae2.init.ModItems;
import cn.dancingsnow.bigger_ae2.integration.appliedflux.AppliedFluxItems;
import cn.dancingsnow.bigger_ae2.item.cell.DigitalSingularityCellItem;

import appeng.api.storage.StorageCells;
import appeng.api.upgrades.Upgrades;
import appeng.core.definitions.AEItems;
import appeng.core.localization.GuiText;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import org.slf4j.Logger;

@Mod(BiggerAE2Mod.MOD_ID)
public class BiggerAE2Mod {

    public static final String MOD_ID = "bigger_ae2";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Registrate REGISTRATE = Registrate.create(MOD_ID);

    public BiggerAE2Mod(IEventBus modEventBus, ModContainer container) {

        ModCreativeTab.register();
        ModItems.register();
        ModBlocks.register();
        ModBlockEntities.register();
        ModComponents.register(modEventBus);

        try {
            Class.forName("com.glodblock.github.appflux.common.me.key.type.FluxKeyType");
            AppliedFluxItems.register();
        } catch (ClassNotFoundException e) {
            BiggerAE2Mod.LOGGER.debug("Applied Flux not installed, passed");
        }

        modEventBus.addListener(BiggerAE2Mod::initUpgrades);
        modEventBus.addListener(BiggerAE2Mod::initStorageCells);
        modEventBus.addListener(BiggerAE2Mod::packSetup);

        REGISTRATE.registerEventListeners(modEventBus);

        BiggerAE2Datagen.init();
    }

    public static ResourceLocation of(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
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

    private static void packSetup(AddPackFindersEvent event) {
        event.addPackFinders(
                BiggerAE2Mod.of("builtin_pack"),
                PackType.CLIENT_RESOURCES,
                Component.translatable("bigger_ae2.old_pack"),
                PackSource.BUILT_IN,
                false,
                Pack.Position.TOP);
    }
}
