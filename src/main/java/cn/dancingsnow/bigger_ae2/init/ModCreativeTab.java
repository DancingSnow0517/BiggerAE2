package cn.dancingsnow.bigger_ae2.init;


import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.world.item.CreativeModeTab;


import static cn.dancingsnow.bigger_ae2.BiggerAE2Mod.REGISTRATE;

public class ModCreativeTab {

    public static final RegistryEntry<CreativeModeTab> TAB = REGISTRATE
        .defaultCreativeTab("bigger_ae2", builder -> builder
            .icon(ModItems.SINGULARITY_ITEM_CELL::asStack)
            .displayItems((parameters, output) -> {
                output.accept(ModItems.ADVANCED_ITEM_CELL_HOUSING);
                output.accept(ModItems.ADVANCED_FLUID_CELL_HOUSING);
                output.accept(ModItems.QUANTUM_CELL_COMPONENT);
                output.accept(ModItems.QUANTUM_ITEM_CELL);
                output.accept(ModItems.QUANTUM_FLUID_CELL);
                output.accept(ModItems.SINGULARITY_CELL_COMPONENT);
                output.accept(ModItems.SINGULARITY_ITEM_CELL);
                output.accept(ModItems.SINGULARITY_FLUID_CELL);
                output.accept(ModBlocks.ACCELERATOR_4);
                output.accept(ModBlocks.ACCELERATOR_16);
                output.accept(ModBlocks.ACCELERATOR_64);
                output.accept(ModBlocks.ACCELERATOR_256);
            })
            .build()
        )
        .register();

    public static void register() {

    }


}
