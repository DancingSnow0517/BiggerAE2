package cn.dancingsnow.bigger_ae2.data.generator;

import cn.dancingsnow.bigger_ae2.data.generator.lang.LangHandler;

import com.tterrag.registrate.providers.ProviderType;

import static cn.dancingsnow.bigger_ae2.BiggerAE2Mod.REGISTRATE;

public class BiggerAE2Datagen {
    public static void init() {
        REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::init);
    }
}
