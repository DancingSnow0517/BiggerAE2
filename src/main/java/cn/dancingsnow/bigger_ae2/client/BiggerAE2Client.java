package cn.dancingsnow.bigger_ae2.client;

import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;
import cn.dancingsnow.bigger_ae2.client.model.UnbakedCraftingUnitModel;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterBlockStateModels;

@Mod(value = BiggerAE2Mod.MOD_ID, dist = Dist.CLIENT)
public class BiggerAE2Client {
    public BiggerAE2Client(IEventBus modEventBus) {
        super();
        modEventBus.addListener(this::registerBlockStateModels);
    }

    private void registerBlockStateModels(RegisterBlockStateModels event) {
        event.registerModel(UnbakedCraftingUnitModel.ID, UnbakedCraftingUnitModel.MAP_CODEC);
    }
}
