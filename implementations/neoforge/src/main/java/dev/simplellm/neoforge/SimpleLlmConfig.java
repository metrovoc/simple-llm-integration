package dev.simplellm.neoforge;

import dev.simplellm.common.config.LlmConfig;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class SimpleLlmConfig {
	public static final ModConfigSpec SPEC;
	
	public static final ModConfigSpec.ConfigValue<String> TRIGGER;
	public static final ModConfigSpec.ConfigValue<String> SYSTEM_PROMPT;
	public static final ModConfigSpec.IntValue CONTEXT_WINDOW;
	public static final ModConfigSpec.IntValue MEMORY_WINDOW;
	
	public static final ModConfigSpec.ConfigValue<String> PROVIDER;
	public static final ModConfigSpec.ConfigValue<String> API_KEY;
	public static final ModConfigSpec.ConfigValue<String> MODEL_NAME;
	public static final ModConfigSpec.ConfigValue<String> BASE_URL;
	
	public static final ModConfigSpec.BooleanValue ENABLE_WEB_SEARCH;
	public static final ModConfigSpec.ConfigValue<String> TAVILY_API_KEY;
	
	static {
		ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
		
		builder.comment("Simple LLM Integration Configuration").push("simple-llm");
		
		TRIGGER = builder
			.comment("Trigger phrase to activate LLM (default: @ui)")
			.define("trigger", LlmConfig.DEFAULT_TRIGGER);
			
		SYSTEM_PROMPT = builder
			.comment("System prompt for the LLM")
			.define("systemPrompt", LlmConfig.DEFAULT_SYSTEM_PROMPT);
			
		CONTEXT_WINDOW = builder
			.comment("Number of recent chat messages to include as context")
			.defineInRange("contextWindow", LlmConfig.DEFAULT_CONTEXT_WINDOW, 1, 200);
			
		MEMORY_WINDOW = builder
			.comment("Number of messages to keep in LLM memory")
			.defineInRange("memoryWindow", LlmConfig.DEFAULT_MEMORY_WINDOW, 1, 50);
		
		builder.comment("LLM Provider Settings").push("provider");
		
		PROVIDER = builder
			.comment("LLM provider (openai, mock)")
			.define("name", "mock");
			
		API_KEY = builder
			.comment("API key for the LLM provider")
			.define("apiKey", "");
			
		MODEL_NAME = builder
			.comment("Model name to use")
			.define("modelName", "gpt-3.5-turbo");
			
		BASE_URL = builder
			.comment("Custom base URL (optional)")
			.define("baseUrl", "");
		
		builder.pop();
		
		builder.comment("Tool Settings").push("tools");
		
		ENABLE_WEB_SEARCH = builder
			.comment("Enable web search tool")
			.define("enableWebSearch", false);
			
		TAVILY_API_KEY = builder
			.comment("Tavily API key for web search")
			.define("tavilyApiKey", "");
		
		builder.pop().pop();
		
		SPEC = builder.build();
	}
	
	public static void register() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SPEC, "simple-llm.toml");
	}
	
	public static LlmConfig toLlmConfig() {
		LlmConfig config = new LlmConfig();
		config.setTrigger(TRIGGER.get());
		config.setSystemPrompt(SYSTEM_PROMPT.get());
		config.setContextWindow(CONTEXT_WINDOW.get());
		config.setMemoryWindow(MEMORY_WINDOW.get());
		config.setProvider(PROVIDER.get());
		config.setApiKey(API_KEY.get());
		config.setModelName(MODEL_NAME.get());
		config.setBaseUrl(BASE_URL.get());
		config.setEnableWebSearch(ENABLE_WEB_SEARCH.get());
		config.setTavilyApiKey(TAVILY_API_KEY.get());
		return config;
	}
}
