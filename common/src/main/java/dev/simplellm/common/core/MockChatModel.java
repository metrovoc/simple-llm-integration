package dev.simplellm.common.core;

import dev.langchain4j.model.chat.ChatModel;

public class MockChatModel implements ChatModel {
	
	@Override
	public String chat(String message) {
		// Simple mock response for testing
		return "Mock AI response: I received your message and context. This is a test response from the mock LLM.";
	}
}
