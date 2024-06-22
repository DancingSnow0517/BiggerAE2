package cn.dancingsnow.bigger_ae2.block;

import cn.dancingsnow.bigger_ae2.init.ModBlocks;

import appeng.block.crafting.CraftingUnitBlock;
import appeng.block.crafting.ICraftingUnitType;

import net.minecraft.world.item.Item;

import com.tterrag.registrate.util.entry.BlockEntry;
import lombok.Getter;

public enum ModCraftingUnitType implements ICraftingUnitType {
    ACCELERATOR_4(4, "4_core_crafting_accelerator"),
    ACCELERATOR_16(16, "16_core_crafting_accelerator"),
    ACCELERATOR_64(64, "64_core_crafting_accelerator"),
    ACCELERATOR_256(256, "256_core_crafting_accelerator"),
    ACCELERATOR_1024(1024, "1024_core_crafting_accelerator");

    private final int accelerator;

    @Getter
    private final String affix;

    ModCraftingUnitType(int accelerator, String affix) {
        this.accelerator = accelerator;
        this.affix = affix;
    }

    @Override
    public long getStorageBytes() {
        return 0;
    }

    @Override
    public int getAcceleratorThreads() {
        return accelerator;
    }

    @Override
    public Item getItemFromType() {
        return switch (this) {
            case ACCELERATOR_4 -> ModBlocks.ACCELERATOR_4.asItem();
            case ACCELERATOR_16 -> ModBlocks.ACCELERATOR_16.asItem();
            case ACCELERATOR_64 -> ModBlocks.ACCELERATOR_64.asItem();
            case ACCELERATOR_256 -> ModBlocks.ACCELERATOR_256.asItem();
            case ACCELERATOR_1024 -> ModBlocks.ACCELERATOR_1024.asItem();
        };
    }

    public BlockEntry<CraftingUnitBlock> getBlockFromType() {
        return switch (this) {
            case ACCELERATOR_4 -> ModBlocks.ACCELERATOR_4;
            case ACCELERATOR_16 -> ModBlocks.ACCELERATOR_16;
            case ACCELERATOR_64 -> ModBlocks.ACCELERATOR_64;
            case ACCELERATOR_256 -> ModBlocks.ACCELERATOR_256;
            case ACCELERATOR_1024 -> ModBlocks.ACCELERATOR_1024;
        };
    }
}
