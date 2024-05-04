package cn.dancingsnow.bigger_ae2;


import cn.dancingsnow.bigger_ae2.init.ModCreativeTab;
import cn.dancingsnow.bigger_ae2.init.ModItems;
import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;

@Mod(BiggerAE2Mod.MOD_ID)
public class BiggerAE2Mod {

    public static final String MOD_ID = "bigger_ae2";
    public static final Logger LOGGER = LogUtils.getLogger();


    public BiggerAE2Mod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModCreativeTab.register(modEventBus);

        modEventBus.addListener(BiggerAE2Mod::registerAll);
    }

    public static ResourceLocation of(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    private static void registerAll(RegisterEvent event) {
        ModItems.getITEMS().forEach(item -> event.register(Registries.ITEM, item.id(), item::asItem));
    }
}
