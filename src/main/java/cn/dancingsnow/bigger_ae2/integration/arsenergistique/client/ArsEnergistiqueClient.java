package cn.dancingsnow.bigger_ae2.integration.arsenergistique.client;

import appeng.api.client.StorageCellModels;
import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;
import cn.dancingsnow.bigger_ae2.client.BiggerAE2Client;
import cn.dancingsnow.bigger_ae2.integration.arsenergistique.ArsEnergistiqueItems;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class ArsEnergistiqueClient {

    public static void initItemColor(RegisterColorHandlersEvent.Item event) {
        event.register(BiggerAE2Client.CELL_COLOR, ArsEnergistiqueItems.QUANTUM_SOURCE_STORAGE_CELL, ArsEnergistiqueItems.SINGULARITY_SOURCE_STORAGE_CELL);
    }

    public static void registerCellModel() {
        StorageCellModels.registerModel(ArsEnergistiqueItems.QUANTUM_SOURCE_STORAGE_CELL, BiggerAE2Mod.of("block/drive/cells/quantum_source_cell"));
        StorageCellModels.registerModel(ArsEnergistiqueItems.SINGULARITY_SOURCE_STORAGE_CELL, BiggerAE2Mod.of("block/drive/cells/digital_singularity_source_cell"));
    }
}
