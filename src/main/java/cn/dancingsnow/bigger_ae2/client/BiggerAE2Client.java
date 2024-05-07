package cn.dancingsnow.bigger_ae2.client;

import appeng.client.render.crafting.CraftingCubeModel;
import appeng.hooks.BuiltInModelHooks;
import cn.dancingsnow.bigger_ae2.BiggerAE2Base;
import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;
import cn.dancingsnow.bigger_ae2.block.ModCraftingUnitType;
import cn.dancingsnow.bigger_ae2.init.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
public class BiggerAE2Client extends BiggerAE2Base {
    public BiggerAE2Client() {
        super();
        initCraftingUnitModels();
    }

    private static void initCraftingUnitModels() {
        for (ModCraftingUnitType type : ModCraftingUnitType.values()) {
            BuiltInModelHooks.addBuiltInModel(
                BiggerAE2Mod.of("block/crafting/" + type.getAffix() + "_formed"),
                new CraftingCubeModel(new ModCraftingUnitModelProvider(type))
            );
        }

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(BiggerAE2Client::setRenderLayer);
    }

    private static void setRenderLayer(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACCELERATOR_4.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACCELERATOR_16.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACCELERATOR_64.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACCELERATOR_256.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACCELERATOR_1024.get(), RenderType.cutout());
    }
}
