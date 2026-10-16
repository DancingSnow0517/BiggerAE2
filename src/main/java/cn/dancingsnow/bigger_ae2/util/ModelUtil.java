package cn.dancingsnow.bigger_ae2.util;

import appeng.core.AppEng;
import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModelUtil {
    private static final ResourceLocation QUANTUM_STORAGE_CELL_LIGHT = BiggerAE2Mod.of("item/quantum_storage_cell_light");
    private static final ResourceLocation DIGITAL_SINGULARITY_STORAGE_CELL_LIGHT = BiggerAE2Mod.of("item/digital_singularity_storage_cell_light");
    private static final ResourceLocation DIGITAL_SINGULARITY_STORAGE_CELL_LAYER = BiggerAE2Mod.of("item/digital_singularity_storage_cell_layer");
    private static final ResourceLocation STORAGE_CELL_LED = AppEng.makeId("item/storage_cell_led");

    private ModelUtil() {
    }

    public static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> quantumItemModel(ResourceLocation housing) {
        return (context, provider) -> provider.generated(
            context,
            housing,
            QUANTUM_STORAGE_CELL_LIGHT,
            STORAGE_CELL_LED
        );
    }

    public static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> digitalSingularityItemModel(ResourceLocation housing) {
        return (context, provider) -> provider.generated(
            context,
            housing,
            DIGITAL_SINGULARITY_STORAGE_CELL_LIGHT,
            STORAGE_CELL_LED,
            DIGITAL_SINGULARITY_STORAGE_CELL_LAYER
        );
    }
}
