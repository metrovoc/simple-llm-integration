package dev.simplellm.common.core;

import dev.simplellm.common.api.ChatEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface LlmService {
	CompletableFuture<String> respond(String systemPrompt, List<ChatEvent> context, String userMessage, String callerInfo);
}
