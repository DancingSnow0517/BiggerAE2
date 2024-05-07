package cn.dancingsnow.bigger_ae2.mixins.client;

import cn.dancingsnow.bigger_ae2.BiggerAE2Mod;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {
    @Shadow protected abstract void cacheAndQueueDependencies(ResourceLocation location, UnbakedModel model);

    @Inject(method = "loadModel", at = @At("HEAD"), cancellable = true)
    private void loadModelHook(ResourceLocation id, CallbackInfo ci) {
        UnbakedModel model = bigger_ae2$getUnbakedModel(id);
        if (model != null) {
            cacheAndQueueDependencies(id, model);
            ci.cancel();
        }
    }

    @Unique
    private UnbakedModel bigger_ae2$getUnbakedModel(ResourceLocation variantId) {
        if (!variantId.getNamespace().equals(BiggerAE2Mod.MOD_ID)) {
            return null;
        }
        if (variantId instanceof ModelResourceLocation modelId) {
            if (modelId.getVariant().equals("inventory")) {
                ResourceLocation itemModelId = new ResourceLocation(modelId.getNamespace(), "item/" + modelId.getPath());
                return BuiltInModelHooksAccessor.getBuiltInModels().get(itemModelId);
            }
            return null;
        } else {
            return BuiltInModelHooksAccessor.getBuiltInModels().get(variantId);
        }
    }
}
