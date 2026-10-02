package silly.homak.itemshadows.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import silly.homak.itemshadows.ShadowRenderState;
import silly.homak.itemshadows.config.ShadowConfig;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Redirect(method = "renderHotbar", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbarItem(Lnet/minecraft/client/gui/DrawContext;IILnet/minecraft/client/render/RenderTickCounter;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V",
    ordinal = 0))
    private void drawItemWithShadow(InGameHud instance, DrawContext context, int x, int y, RenderTickCounter tickCounter, PlayerEntity player, ItemStack stack, int seed) {
        if (stack.isEmpty()) return;

        ShadowConfig config = ShadowConfig.HANDLER.instance();

        if (!config.enableMod) {
            context.drawItem(stack, x, y, seed);
            return;
        }

        ShadowRenderState.isDepthPrePass = true;
        context.drawItem(stack, x + 1, y + 1, seed, 0);
        ShadowRenderState.isDepthPrePass = false;

        RenderSystem.depthFunc(GL11.GL_EQUAL);
        ShadowRenderState.isShadowColorPass = true;

        float alpha = config.shadowOpacity / 100.0f;
        RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, alpha);

        context.drawItem(stack, x + 1, y + 1, seed, 0);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        ShadowRenderState.isShadowColorPass = false;

        context.drawItem(stack, x, y, seed, 3);
    }
    @Redirect(method = "renderHotbar", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbarItem(Lnet/minecraft/client/gui/DrawContext;IILnet/minecraft/client/render/RenderTickCounter;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V",
            ordinal = 1))
    private void drawOffhandItemWithShadow(InGameHud instance, DrawContext context, int x, int y, RenderTickCounter tickCounter, PlayerEntity player, ItemStack stack, int seed) {
        if (stack.isEmpty()) return;

        ShadowConfig config = ShadowConfig.HANDLER.instance();

        if (!config.enableMod) {
            context.drawItem(stack, x, y, seed);
            return;
        }

        ShadowRenderState.isDepthPrePass = true;
        context.drawItem(stack, x + 1, y + 1, seed, 0);
        ShadowRenderState.isDepthPrePass = false;

        RenderSystem.depthFunc(GL11.GL_EQUAL);
        ShadowRenderState.isShadowColorPass = true;

        float alpha = config.shadowOpacity / 100.0f;
        RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, alpha);

        context.drawItem(stack, x + 1, y + 1, seed, 0);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        ShadowRenderState.isShadowColorPass = false;

        context.drawItem(stack, x, y, seed, 3);
    }
}
