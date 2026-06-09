package cn.dancingsnow.bigger_ae2.client;

import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;
import cn.dancingsnow.bigger_ae2.block.ModCraftingUnitType;

import appeng.client.render.crafting.AbstractCraftingUnitModelProvider;
import appeng.client.render.crafting.LightBakedModel;
import appeng.core.AppEng;

import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.MaterialBaker;
import org.jspecify.annotations.NonNull;

public class ModCraftingUnitModelProvider extends AbstractCraftingUnitModelProvider<ModCraftingUnitType> implements ModelDebugName {

    private static final Material RING_CORNER = aeTexture("ring_corner");
    private static final Material RING_SIDE_HOR = aeTexture("ring_side_hor");
    private static final Material RING_SIDE_VER = aeTexture("ring_side_ver");
    private static final Material LIGHT_BASE = aeTexture("light_base");

    private static final Material ACCELERATOR_4_LIGHT = texture("4_core_crafting_accelerator_light");
    private static final Material ACCELERATOR_16_LIGHT = texture("16_core_crafting_accelerator_light");
    private static final Material ACCELERATOR_64_LIGHT = texture("64_core_crafting_accelerator_light");
    private static final Material ACCELERATOR_256_LIGHT = texture("256_core_crafting_accelerator_light");
    private static final Material ACCELERATOR_1024_LIGHT = texture("1024_core_crafting_accelerator_light");

    public ModCraftingUnitModelProvider(ModCraftingUnitType type) {
        super(type);
    }

    public Material.Baked getLightMaterial(MaterialBaker materialBaker) {
        return switch (this.type) {
            case ACCELERATOR_4 -> materialBaker.get(ACCELERATOR_4_LIGHT, this);
            case ACCELERATOR_16 -> materialBaker.get(ACCELERATOR_16_LIGHT, this);
            case ACCELERATOR_64 -> materialBaker.get(ACCELERATOR_64_LIGHT, this);
            case ACCELERATOR_256 -> materialBaker.get(ACCELERATOR_256_LIGHT, this);
            case ACCELERATOR_1024 -> materialBaker.get(ACCELERATOR_1024_LIGHT, this);
        };
    }

    @Override
    public BlockStateModel bake(MaterialBaker materialBaker) {
        Material.Baked ringCorner = materialBaker.get(RING_CORNER, this);
        Material.Baked ringSideHor = materialBaker.get(RING_SIDE_HOR, this);
        Material.Baked ringSideVer = materialBaker.get(RING_SIDE_VER, this);


        return new LightBakedModel(
            ringCorner,
            ringSideHor,
            ringSideVer,
            materialBaker.get(LIGHT_BASE, this),
            getLightMaterial(materialBaker)
        );
    }

    @Override
    public @NonNull String debugName() {
        return getClass().toString();
    }

    private static Material texture(String name) {
        return new Material(BiggerAE2Mod.of("block/crafting/" + name));
    }

    private static Material aeTexture(String name) {
        return new Material(AppEng.makeId("block/crafting/" + name));
    }
}
