package cn.dancingsnow.bigger_ae2.integration.appliedexperienced.client;

import appeng.api.client.StorageCellModels;
import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;
import cn.dancingsnow.bigger_ae2.client.BiggerAE2Client;
import cn.dancingsnow.bigger_ae2.integration.appliedexperienced.AppliedExperiencedItems;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class AppliedExperiencedClient {

    public static void initItemColor(RegisterColorHandlersEvent.Item event) {
        event.register(BiggerAE2Client.CELL_COLOR, AppliedExperiencedItems.QUANTUM_EXPERIENCE_CELL, AppliedExperiencedItems.SINGULARITY_EXPERIENCE_CELL);
    }

    public static void registerCellModel() {
        StorageCellModels.registerModel(AppliedExperiencedItems.QUANTUM_EXPERIENCE_CELL, BiggerAE2Mod.of("block/drive/cells/quantum_experience_cell"));
        StorageCellModels.registerModel(AppliedExperiencedItems.SINGULARITY_EXPERIENCE_CELL, BiggerAE2Mod.of("block/drive/cells/digital_singularity_experience_cell"));
    }
}
