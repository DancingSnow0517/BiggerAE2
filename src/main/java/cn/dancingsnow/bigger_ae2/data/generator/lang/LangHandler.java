package cn.dancingsnow.bigger_ae2.data.generator.lang;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class LangHandler {
    public static void init(RegistrateLangProvider provider) {
        provider.add("tooltip.bigger_ae2.contains", "Contains: %s");
        provider.add("tooltip.bigger_ae2.quantity", "Quantity: %s");
        provider.add("tooltip.bigger_ae2.empty", "Empty");
        provider.add("tooltip.bigger_ae2.partitioned", "Partitioned for: %s");
        provider.add("bigger_ae2.old_pack", "Bigger AE2 Old Textures");
    }
}
