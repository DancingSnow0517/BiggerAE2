package cn.dancingsnow.bigger_ae2.mixins;

import appeng.me.cluster.implementations.CraftingCPUCluster;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CraftingCPUCluster.class)
public class CraftingCPUClusterMixin {

    @ModifyConstant(method = "addBlockEntity", remap = false, constant = @Constant(intValue = 16))
    private int modifyLimit(int constant) {
        return 1024;
    }
}
