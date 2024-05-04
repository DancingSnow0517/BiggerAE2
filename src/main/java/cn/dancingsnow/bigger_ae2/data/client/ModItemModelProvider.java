package cn.dancingsnow.bigger_ae2.data.client;

import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;
import cn.dancingsnow.bigger_ae2.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BiggerAE2Mod.MOD_ID, existingFileHelper);
    }

    private final ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

    @Override
    protected void registerModels() {
        ModItems.getITEMS().forEach(item -> builder(itemGenerated, item.id().getPath()));
    }

    private void builder(ModelFile itemGenerated, String name) {
        getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
}
