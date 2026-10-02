package silly.homak.itemshadows.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ShadowConfigScreen {

    public static Screen createScreen(Screen parentScreen) {
        ShadowConfig config = ShadowConfig.HANDLER.instance();

        return YetAnotherConfigLib.createBuilder()
                .title(Text.literal("Item Shadows Configuration"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("General Settings"))
                        .tooltip(Text.literal("Core tweaks for the mod"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Shadow Settings"))

                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Enable Item Shadows"))
                                        .description(OptionDescription.of(Text.literal("Toggles item shadows on or off globally.")))
                                        .binding(
                                                true,
                                                () -> config.enableMod,
                                                value -> config.enableMod = value
                                        )
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())

                                .option(Option.<Integer>createBuilder()
                                        .name(Text.literal("Shadow Opacity"))
                                        .description(OptionDescription.of(Text.literal("Adjust the transparency of the item shadows.")))
                                        .binding(
                                                35,
                                                () -> config.shadowOpacity,
                                                value -> config.shadowOpacity = value
                                        )
                                        .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                                .range(0, 100)
                                                .step(1))
                                        .build())
                                .build())
                        .build())

                .save(ShadowConfig.HANDLER::save)
                .build()
                .generateScreen(parentScreen);
    }
}