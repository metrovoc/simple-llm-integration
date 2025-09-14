package dev.simplellm.neoforge;

import dev.simplellm.common.core.LlmServiceFactory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("simplellm")
public class SimpleLlmMod {
	public static final Logger LOGGER = LogManager.getLogger("simplellm");

	public SimpleLlmMod(IEventBus modEventBus, ModContainer container) {
		LOGGER.info("simplellm mod initializing");
		
		// Register config
		SimpleLlmConfig.register(container);
		
		// Listen to mod bus events
		modEventBus.addListener(this::onCommonSetup);
		modEventBus.addListener(this::onConfigLoad);
		modEventBus.addListener(this::onConfigReload);
	}
	
	private void onCommonSetup(FMLCommonSetupEvent event) {
		LOGGER.info("simplellm common setup");
		initializeLlmService();
	}
	
	private void onConfigLoad(ModConfigEvent.Loading event) {
		LOGGER.info("simplellm config loaded");
		initializeLlmService();
	}
	
	private void onConfigReload(ModConfigEvent.Reloading event) {
		LOGGER.info("simplellm config reloaded");
		initializeLlmService();
	}
	
	private void initializeLlmService() {
		try {
			SimpleLlmRuntime.setService(LlmServiceFactory.create(SimpleLlmConfig.toLlmConfig()));
			SimpleLlmRuntime.updateFromConfig(SimpleLlmConfig.toLlmConfig());
			LOGGER.info("LLM service initialized with provider: {}", SimpleLlmConfig.PROVIDER.get());
		} catch (Exception e) {
			LOGGER.error("Failed to initialize LLM service", e);
		}
	}
}
