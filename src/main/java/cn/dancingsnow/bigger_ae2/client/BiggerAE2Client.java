package cn.dancingsnow.bigger_ae2.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import appeng.client.render.crafting.CraftingCubeModel;
import appeng.hooks.BuiltInModelHooks;
import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;
import cn.dancingsnow.bigger_ae2.block.ModCraftingUnitType;
import cn.dancingsnow.bigger_ae2.init.ModBlocks;

@Mod(value = BiggerAE2Mod.MOD_ID, dist = Dist.CLIENT)
public class BiggerAE2Client {
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
    }

    private static void setRenderLayer(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACCELERATOR_4.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACCELERATOR_16.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACCELERATOR_64.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACCELERATOR_256.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACCELERATOR_1024.get(), RenderType.cutout());
    }
}
