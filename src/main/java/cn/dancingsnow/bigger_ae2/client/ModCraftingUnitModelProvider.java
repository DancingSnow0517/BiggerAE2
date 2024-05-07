package cn.dancingsnow.bigger_ae2.client;

import appeng.client.render.crafting.AbstractCraftingUnitModelProvider;
import appeng.client.render.crafting.LightBakedModel;
import appeng.core.AppEng;
import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;
import cn.dancingsnow.bigger_ae2.block.ModCraftingUnitType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class ModCraftingUnitModelProvider extends AbstractCraftingUnitModelProvider<ModCraftingUnitType> {

    private static final List<Material> MATERIALS = new ArrayList<>();

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

    public TextureAtlasSprite getLightMaterial(Function<Material, TextureAtlasSprite> textureGetter) {
        return switch (this.type) {
            case ACCELERATOR_4 -> textureGetter.apply(ACCELERATOR_4_LIGHT);
            case ACCELERATOR_16 -> textureGetter.apply(ACCELERATOR_16_LIGHT);
            case ACCELERATOR_64 -> textureGetter.apply(ACCELERATOR_64_LIGHT);
            case ACCELERATOR_256 -> textureGetter.apply(ACCELERATOR_256_LIGHT);
            case ACCELERATOR_1024 -> textureGetter.apply(ACCELERATOR_1024_LIGHT);
        };
    }

    @Override
    public List<Material> getMaterials() {
        return Collections.unmodifiableList(MATERIALS);
    }

    @Override
    public BakedModel getBakedModel(Function<Material, TextureAtlasSprite> spriteGetter) {
        TextureAtlasSprite ringCorner = spriteGetter.apply(RING_CORNER);
        TextureAtlasSprite ringSideHor = spriteGetter.apply(RING_SIDE_HOR);
        TextureAtlasSprite ringSideVer = spriteGetter.apply(RING_SIDE_VER);
        TextureAtlasSprite lightBase = spriteGetter.apply(LIGHT_BASE);
        return new LightBakedModel(
            ringCorner,
            ringSideHor,
            ringSideVer,
            lightBase,
            getLightMaterial(spriteGetter)
        );
    }

    private static Material texture(String name) {
        var material = new Material(InventoryMenu.BLOCK_ATLAS, BiggerAE2Mod.of("block/crafting/" + name));
        MATERIALS.add(material);
        return material;
    }

    private static Material aeTexture(String name) {
        var material = new Material(InventoryMenu.BLOCK_ATLAS, AppEng.makeId("block/crafting/" + name));
        MATERIALS.add(material);
        return material;
    }
}
