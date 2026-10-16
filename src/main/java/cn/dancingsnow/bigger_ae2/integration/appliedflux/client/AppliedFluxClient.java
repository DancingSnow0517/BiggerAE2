package cn.dancingsnow.bigger_ae2.integration.appliedflux.client;

import appeng.api.client.StorageCellModels;
import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;
import cn.dancingsnow.bigger_ae2.client.BiggerAE2Client;
import cn.dancingsnow.bigger_ae2.integration.appliedflux.AppliedFluxItems;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class AppliedFluxClient {

    public static void initItemColor(RegisterColorHandlersEvent.Item event) {
        event.register(BiggerAE2Client.CELL_COLOR, AppliedFluxItems.QUANTUM_FLUX_CELL, AppliedFluxItems.SINGULARITY_FLUX_CELL);
    }

    public static void registerCellModel() {
        StorageCellModels.registerModel(AppliedFluxItems.QUANTUM_FLUX_CELL, BiggerAE2Mod.of("block/drive/cells/quantum_fe_cell"));
        StorageCellModels.registerModel(AppliedFluxItems.SINGULARITY_FLUX_CELL, BiggerAE2Mod.of("block/drive/cells/digital_singularity_fe_cell"));
    }
}
