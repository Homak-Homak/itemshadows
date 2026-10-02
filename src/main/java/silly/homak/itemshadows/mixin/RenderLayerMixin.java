package silly.homak.itemshadows.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import silly.homak.itemshadows.ShadowRenderState;

@Mixin(RenderPhase.class)
public class RenderLayerMixin {

    @Inject(method = "startDrawing", at = @At("TAIL"))
    private void onStartDrawing(CallbackInfo ci) {
        if (ShadowRenderState.isDepthPrePass) {
            RenderSystem.depthMask(true);
            RenderSystem.colorMask(false, false, false, false);
        } else if (ShadowRenderState.isShadowColorPass) {
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(
                    GlStateManager.SrcFactor.SRC_ALPHA,
                    GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
                    GlStateManager.SrcFactor.ONE,
                    GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA
            );
            RenderSystem.colorMask(true, true, true, true);
            RenderSystem.depthMask(false);
        }
    }

    @Inject(method = "endDrawing", at = @At("HEAD"))
    private void onEndDrawing(CallbackInfo ci) {
        if (ShadowRenderState.isDepthPrePass || ShadowRenderState.isShadowColorPass) {
            RenderSystem.colorMask(true, true, true, true);
            RenderSystem.depthMask(true);
        }
    }
}