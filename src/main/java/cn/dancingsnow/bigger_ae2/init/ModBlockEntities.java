package cn.dancingsnow.bigger_ae2.init;

import appeng.blockentity.AEBaseBlockEntity;
import appeng.blockentity.crafting.CraftingBlockEntity;
import cn.dancingsnow.bigger_ae2.block.ModCraftingUnitType;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static cn.dancingsnow.bigger_ae2.BiggerAE2Mod.REGISTRATE;

public class ModBlockEntities {
    public static BlockEntityEntry<CraftingBlockEntity> CRAFTING_ACCELERATOR = REGISTRATE
        .blockEntity("crafting_accelerator", CraftingBlockEntity::new)
        .validBlocks(
            ModBlocks.ACCELERATOR_4,
            ModBlocks.ACCELERATOR_16,
            ModBlocks.ACCELERATOR_64,
            ModBlocks.ACCELERATOR_256,
            ModBlocks.ACCELERATOR_1024
        )
        .onRegister(type -> {
            for (ModCraftingUnitType craftingUnitType : ModCraftingUnitType.values()) {
                AEBaseBlockEntity.registerBlockEntityItem(type, craftingUnitType.getItemFromType());
                craftingUnitType.getBlockFromType().setBlockEntity(CraftingBlockEntity.class, type, null, null);
            }
        })
        .register();

    public static void register() {

    }
}
