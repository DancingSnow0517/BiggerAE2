package cn.dancingsnow.bigger_ae2.init;

import net.minecraft.world.item.CreativeModeTab;

import com.tterrag.registrate.util.entry.RegistryEntry;

import static cn.dancingsnow.bigger_ae2.BiggerAE2Mod.REGISTRATE;

public class ModCreativeTab {

    public static final RegistryEntry<CreativeModeTab, CreativeModeTab> TAB = REGISTRATE
            .defaultCreativeTab("bigger_ae2", builder -> builder
                    .icon(() -> ModItems.SINGULARITY_ITEM_CELL.get().getDefaultInstance())
                    .build())
            .register();

    public static void register() {}
}
