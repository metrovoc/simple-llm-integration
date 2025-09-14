package dev.simplellm.common.config;

public class LlmConfig {
	public static final String DEFAULT_TRIGGER = "@ui";
	public static final String DEFAULT_SYSTEM_PROMPT = "You are a helpful Minecraft server assistant. Use tools when needed.";
	public static final int DEFAULT_CONTEXT_WINDOW = 50;
	public static final int DEFAULT_MEMORY_WINDOW = 10;
	public static final String DEFAULT_AI_NAME = "AI";

	private String trigger = DEFAULT_TRIGGER;
	private String systemPrompt = DEFAULT_SYSTEM_PROMPT;
	private int contextWindow = DEFAULT_CONTEXT_WINDOW;
	private int memoryWindow = DEFAULT_MEMORY_WINDOW;
	private String aiName = DEFAULT_AI_NAME;
	
	// LLM Provider settings
	private String provider = "openai"; // openai, mock, etc.
	private String apiKey = "";
	private String modelName = "gpt-3.5-turbo";
	private String baseUrl = "";
	
	// Tools
	private boolean enableWebSearch = false;
	private String tavilyApiKey = "";

	public String getTrigger() { return trigger; }
	public void setTrigger(String trigger) { this.trigger = trigger; }

	public String getSystemPrompt() { return systemPrompt; }
	public void setSystemPrompt(String systemPrompt) { this.systemPrompt = systemPrompt; }

	public int getContextWindow() { return contextWindow; }
	public void setContextWindow(int contextWindow) { this.contextWindow = contextWindow; }

	public int getMemoryWindow() { return memoryWindow; }
	public void setMemoryWindow(int memoryWindow) { this.memoryWindow = memoryWindow; }

	public String getProvider() { return provider; }
	public void setProvider(String provider) { this.provider = provider; }

	public String getApiKey() { return apiKey; }
	public void setApiKey(String apiKey) { this.apiKey = apiKey; }

	public String getModelName() { return modelName; }
	public void setModelName(String modelName) { this.modelName = modelName; }

	public String getBaseUrl() { return baseUrl; }
	public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }

	public boolean isEnableWebSearch() { return enableWebSearch; }
	public void setEnableWebSearch(boolean enableWebSearch) { this.enableWebSearch = enableWebSearch; }

	public String getTavilyApiKey() { return tavilyApiKey; }
	public void setTavilyApiKey(String tavilyApiKey) { this.tavilyApiKey = tavilyApiKey; }

	public String getAiName() { return aiName; }
	public void setAiName(String aiName) { this.aiName = aiName; }
}
