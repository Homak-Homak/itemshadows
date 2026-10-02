package silly.homak.itemshadows;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import silly.homak.itemshadows.config.ShadowConfig;

public class ItemShadows implements ClientModInitializer {
	public static final String MOD_ID = "itemshadows";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		ShadowConfig.HANDLER.load();
	}
}