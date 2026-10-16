package cn.dancingsnow.bigger_ae2.client;

import appeng.api.client.StorageCellModels;
import appeng.api.storage.StorageCells;
import appeng.api.storage.cells.CellState;
import appeng.api.storage.cells.StorageCell;
import appeng.client.render.crafting.CraftingCubeModel;
import appeng.hooks.BuiltInModelHooks;
import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;
import cn.dancingsnow.bigger_ae2.block.ModCraftingUnitType;
import cn.dancingsnow.bigger_ae2.init.ModBlocks;
import cn.dancingsnow.bigger_ae2.init.ModItems;
import cn.dancingsnow.bigger_ae2.integration.appliedexperienced.client.AppliedExperiencedClient;
import cn.dancingsnow.bigger_ae2.integration.appliedflux.client.AppliedFluxClient;
import cn.dancingsnow.bigger_ae2.integration.appliedmekanistics.client.AppliedMekanisticsClient;
import cn.dancingsnow.bigger_ae2.integration.arsenergistique.client.ArsEnergistiqueClient;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@Mod(value = BiggerAE2Mod.MOD_ID, dist = Dist.CLIENT)
public class BiggerAE2Client {
    public static final ItemColor CELL_COLOR = (stack, tintIndex) -> {
        if (tintIndex == 2) {
            StorageCell cellInventory = StorageCells.getCellInventory(stack, null);
            CellState cellStatus = cellInventory != null ? cellInventory.getStatus() : CellState.EMPTY;
            return FastColor.ARGB32.opaque(cellStatus.getStateColor());
        } else {
            return 0xFFFFFFFF;
        }
    };

    public BiggerAE2Client(IEventBus modEventBus) {
        super();
        initCraftingUnitModels(modEventBus);
    }

    private static void initCraftingUnitModels(IEventBus modEventBus) {
        for (ModCraftingUnitType type : ModCraftingUnitType.values()) {
            BuiltInModelHooks.addBuiltInModel(
                BiggerAE2Mod.of("block/crafting/" + type.getAffix() + "_formed"),
                new CraftingCubeModel(new ModCraftingUnitModelProvider(type)));
        }

        modEventBus.addListener(BiggerAE2Client::setRenderLayer);
        modEventBus.addListener(BiggerAE2Client::registerItemColor);
        modEventBus.addListener(BiggerAE2Client::registerCellModel);
    }

    @SuppressWarnings("deprecation")
    private static void setRenderLayer(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACCELERATOR_4.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACCELERATOR_16.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACCELERATOR_64.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACCELERATOR_256.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACCELERATOR_1024.get(), RenderType.cutout());
    }

    private static void registerItemColor(RegisterColorHandlersEvent.Item event) {
        event.register(CELL_COLOR, ModItems.QUANTUM_ITEM_CELL, ModItems.QUANTUM_FLUID_CELL, ModItems.SINGULARITY_ITEM_CELL, ModItems.SINGULARITY_FLUID_CELL);
        if (ModList.get().isLoaded("appflux")) {
            AppliedFluxClient.initItemColor(event);
        } else {
            BiggerAE2Mod.LOGGER.debug("Applied Flux not installed, passed");
        }

        if (ModList.get().isLoaded("appmek")) {
            AppliedMekanisticsClient.initItemColor(event);
        } else {
            BiggerAE2Mod.LOGGER.debug("Applied Mekanistics not installed, passed");
        }

        if (ModList.get().isLoaded("arseng")) {
            ArsEnergistiqueClient.initItemColor(event);
        } else {
            BiggerAE2Mod.LOGGER.debug("Ars ArsEnergistique not installed, passed");
        }

        if (ModList.get().isLoaded("appex")) {
            AppliedExperiencedClient.initItemColor(event);
        } else {
            BiggerAE2Mod.LOGGER.debug("Applied Experienced not installed, passed");
        }
    }

    private static void registerCellModel(ModelEvent.RegisterAdditional event) {
        StorageCellModels.registerModel(ModItems.QUANTUM_ITEM_CELL, BiggerAE2Mod.of("block/drive/cells/quantum_item_cell"));
        StorageCellModels.registerModel(ModItems.QUANTUM_FLUID_CELL, BiggerAE2Mod.of("block/drive/cells/quantum_fluid_cell"));
        StorageCellModels.registerModel(ModItems.SINGULARITY_ITEM_CELL, BiggerAE2Mod.of("block/drive/cells/digital_singularity_item_cell"));
        StorageCellModels.registerModel(ModItems.SINGULARITY_FLUID_CELL, BiggerAE2Mod.of("block/drive/cells/digital_singularity_fluid_cell"));

        if (ModList.get().isLoaded("appflux")) {
            AppliedFluxClient.registerCellModel();
        } else {
            BiggerAE2Mod.LOGGER.debug("Applied Flux not installed, passed");
        }

        if (ModList.get().isLoaded("appmek")) {
            AppliedMekanisticsClient.registerCellModel();
        } else {
            BiggerAE2Mod.LOGGER.debug("Applied Mekanistics not installed, passed");
        }

        if (ModList.get().isLoaded("arseng")) {
            ArsEnergistiqueClient.registerCellModel();
        } else {
            BiggerAE2Mod.LOGGER.debug("Ars ArsEnergistique not installed, passed");
        }

        if (ModList.get().isLoaded("appex")) {
            AppliedExperiencedClient.registerCellModel();
        } else {
            BiggerAE2Mod.LOGGER.debug("Applied Experienced not installed, passed");
        }
    }
}
