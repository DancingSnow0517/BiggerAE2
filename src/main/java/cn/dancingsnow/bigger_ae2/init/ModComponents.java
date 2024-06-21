package cn.dancingsnow.bigger_ae2.init;

import cn.dancingsnow.bigger_ae2.item.cell.DigitalSingularityStorage;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;

import java.util.function.Consumer;

public class ModComponents {

    public static final DeferredRegister<DataComponentType<?>> DR =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, BiggerAE2Mod.MOD_ID);

    public static final DataComponentType<DigitalSingularityStorage> SINGULARITY_STORAGE = register(
            "singularity_storage",
            builder -> builder
                    .persistent(DigitalSingularityStorage.CODEC)
                    .networkSynchronized(DigitalSingularityStorage.STREAM_CODEC)
    );

    private static <T> DataComponentType<T> register(
            String name, Consumer<DataComponentType.Builder<T>> customizer) {
        var builder = DataComponentType.<T>builder();
        customizer.accept(builder);
        var componentType = builder.build();
        DR.register(name, () -> componentType);
        return componentType;
    }

    public static void register(IEventBus bus) {
        DR.register(bus);
    }
}
