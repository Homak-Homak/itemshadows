package silly.homak.itemshadows.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import silly.homak.itemshadows.ItemShadows;

public class ShadowConfig {
    public static ConfigClassHandler<ShadowConfig> HANDLER = ConfigClassHandler.createBuilder(ShadowConfig.class)
            .id(Identifier.of(ItemShadows.MOD_ID, "shadowconfig"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("item_shadows.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public boolean enableMod = true;

    @SerialEntry
    public int shadowOpacity = 35;
}