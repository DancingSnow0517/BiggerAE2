package cn.dancingsnow.bigger_ae2.mixins;

import appeng.blockentity.crafting.CraftingBlockEntity;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingCPUCluster.class)
public class CraftingCPUClusterMixin {

//    @ModifyConstant(
//        method = "addBlockEntity",
//        remap = false,
//        constant = @Constant(intValue = 16),
//        require = 0
//    )
//    private int modifyLimit(int constant) {
//        return 1024;
//    }
    @Shadow(remap = false)
    private int accelerator;

    @Inject(method = "addBlockEntity", at = @At("TAIL"), remap = false)
    private void onAddBlockEntity(CraftingBlockEntity te, CallbackInfo ci) {
        try {
            // 如果有 accelerator threads 并且显著大于历史常量 16，则做补偿（将超出部分加回）
            int threads = te.getAcceleratorThreads();
            if (threads > 16) {
                // 这里的策略是把超出历史 cap 的部分加入 accelerator
                int extra = threads - 16;
                this.accelerator += extra;
            }
        } catch (Throwable ignored) {
            // 任何异常都吞掉，避免 mixin 导致服务器崩溃（安全性优先）
        }
    }
}
