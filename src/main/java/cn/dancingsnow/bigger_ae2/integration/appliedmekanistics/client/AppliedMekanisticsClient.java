package cn.dancingsnow.bigger_ae2.integration.appliedmekanistics.client;

import appeng.api.client.StorageCellModels;
import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;
import cn.dancingsnow.bigger_ae2.client.BiggerAE2Client;
import cn.dancingsnow.bigger_ae2.integration.appliedmekanistics.AppliedMekanisticsItems;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class AppliedMekanisticsClient {

    public static void initItemColor(RegisterColorHandlersEvent.Item event) {
        event.register(BiggerAE2Client.CELL_COLOR, AppliedMekanisticsItems.QUANTUM_CHEMICAL_STORAGE_CELL, AppliedMekanisticsItems.SINGULARITY_CHEMICAL_STORAGE_CELL);
    }

    public static void registerCellModel() {
        StorageCellModels.registerModel(AppliedMekanisticsItems.QUANTUM_CHEMICAL_STORAGE_CELL, BiggerAE2Mod.of("block/drive/cells/quantum_chemical_cell"));
        StorageCellModels.registerModel(AppliedMekanisticsItems.SINGULARITY_CHEMICAL_STORAGE_CELL, BiggerAE2Mod.of("block/drive/cells/digital_singularity_chemical_cell"));
    }
}
