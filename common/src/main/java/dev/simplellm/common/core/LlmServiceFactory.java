package dev.simplellm.common.core;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.simplellm.common.config.LlmConfig;

import java.time.Duration;

public class LlmServiceFactory {
	
	public static LlmService create(LlmConfig config) {
		ChatModel chatModel = createChatModel(config);
		return new LangChain4jLlmService(chatModel, config.getMemoryWindow());
	}
	
	private static ChatModel createChatModel(LlmConfig config) {
		switch (config.getProvider().toLowerCase()) {
			case "openai":
				return createOpenAiModel(config);
			case "mock":
				return createMockModel();
			default:
				throw new IllegalArgumentException("Unsupported provider: " + config.getProvider());
		}
	}
	
	private static ChatModel createOpenAiModel(LlmConfig config) {
		return OpenAiChatModel.builder()
			.apiKey(config.getApiKey())
			.modelName(config.getModelName())
			.timeout(Duration.ofSeconds(30))
			.build();
	}
	
	private static ChatModel createMockModel() {
		return new MockChatModel();
	}
}
