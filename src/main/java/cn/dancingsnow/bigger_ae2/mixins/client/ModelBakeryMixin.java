package cn.dancingsnow.bigger_ae2.mixins.client;

import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

    @Inject(method = "getModel", at = @At("HEAD"), cancellable = true)
    private void loadModelHook(ResourceLocation id, CallbackInfoReturnable<UnbakedModel> cir) {
        UnbakedModel model = bigger_ae2$getUnbakedModel(id);

        if (model != null) {
            cir.setReturnValue(model);
        }
    }

    @Unique private UnbakedModel bigger_ae2$getUnbakedModel(ResourceLocation variantId) {
        if (!variantId.getNamespace().equals(BiggerAE2Mod.MOD_ID)) {
            return null;
        }

        return BuiltInModelHooksAccessor.getBuiltInModels().get(variantId);
    }
}
