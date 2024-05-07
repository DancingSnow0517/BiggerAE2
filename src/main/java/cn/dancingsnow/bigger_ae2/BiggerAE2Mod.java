package cn.dancingsnow.bigger_ae2;


import cn.dancingsnow.bigger_ae2.client.BiggerAE2Client;
import com.mojang.logging.LogUtils;

import com.tterrag.registrate.Registrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(BiggerAE2Mod.MOD_ID)
public class BiggerAE2Mod {

    public static final String MOD_ID = "bigger_ae2";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Registrate REGISTRATE = Registrate.create(MOD_ID);


    public BiggerAE2Mod() {
        DistExecutor.unsafeRunForDist(() -> BiggerAE2Client::new, () -> BiggerAE2Server::new);
    }

    public static ResourceLocation of(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
