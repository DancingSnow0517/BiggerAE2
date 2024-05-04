package cn.dancingsnow.bigger_ae2.data;

import cn.dancingsnow.bigger_ae2.data.client.ModItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BiggerAE2Datagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeClient()) {
            generator.addProvider(true, new ModItemModelProvider(generator.getPackOutput(), event.getExistingFileHelper()));
        }
    }
}
