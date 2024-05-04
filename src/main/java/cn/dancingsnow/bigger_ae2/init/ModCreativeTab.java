package cn.dancingsnow.bigger_ae2.init;


import appeng.block.AEBaseBlock;
import appeng.block.AEBaseBlockItem;
import appeng.core.definitions.ItemDefinition;
import appeng.items.AEBaseItem;
import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BiggerAE2Mod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_TAB.register(
        "tab",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("creativeTab.bigger_ae2.tab"))
            .icon(ModItems.ITEM_CELL_SINGULARITY::stack)
            .displayItems(ModCreativeTab::populateTab)
            .build()
    );

    public static void register(IEventBus bus) {
        CREATIVE_TAB.register(bus);
    }

    private static void populateTab(CreativeModeTab.ItemDisplayParameters ignored, CreativeModeTab.Output output) {
        List<ItemDefinition<?>> itemDefinitions = new ArrayList<>();
        itemDefinitions.addAll(ModItems.getITEMS());

        for (ItemDefinition<?> itemDefinition : itemDefinitions) {
            Item item = itemDefinition.asItem();
            if (item instanceof AEBaseBlockItem baseBlockItem && baseBlockItem.getBlock() instanceof AEBaseBlock baseBlock) {
                baseBlock.addToMainCreativeTab(output);
            } else if (item instanceof AEBaseItem baseItem) {
                baseItem.addToMainCreativeTab(output);
            } else {
                output.accept(itemDefinition);
            }
        }
    }
}
