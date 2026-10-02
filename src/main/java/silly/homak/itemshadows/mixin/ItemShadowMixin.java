package silly.homak.itemshadows.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import silly.homak.itemshadows.ShadowRenderState;
import silly.homak.itemshadows.config.ShadowConfig;

@Mixin(DrawContext.class)
public abstract class ItemShadowMixin {

    @Redirect(
            method = "drawItem(Lnet/minecraft/item/ItemStack;III)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;III)V"
            )
    )
    private void drawItemWithShadow(DrawContext instance, LivingEntity entity, World world, ItemStack stack, int x, int y, int seed) {
        if (stack.isEmpty()) return;

        ShadowConfig config = ShadowConfig.HANDLER.instance();

        if (!config.enableMod) {
            instance.drawItem(stack, x, y, seed);
            return;
        }

        ShadowRenderState.isDepthPrePass = true;
        instance.drawItem(stack, x + 1, y + 1, seed, 0);
        ShadowRenderState.isDepthPrePass = false;

        RenderSystem.depthFunc(GL11.GL_EQUAL);
        ShadowRenderState.isShadowColorPass = true;

        float alpha = config.shadowOpacity / 100.0f;
        RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, alpha);

        instance.drawItem(stack, x + 1, y + 1, seed, 0);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        ShadowRenderState.isShadowColorPass = false;

        instance.drawItem(stack, x, y, seed, 3);
    }
}