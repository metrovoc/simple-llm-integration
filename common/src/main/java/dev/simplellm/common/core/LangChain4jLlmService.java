package dev.simplellm.common.core;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.simplellm.common.api.ChatEvent;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class LangChain4jLlmService implements LlmService {
	private final ChatModel chatModel;
	private final ChatMemory chatMemory;

	public LangChain4jLlmService(ChatModel chatModel, int memoryWindow) {
		this.chatModel = Objects.requireNonNull(chatModel, "chatModel");
		this.chatMemory = MessageWindowChatMemory.withMaxMessages(Math.max(4, memoryWindow));
	}

	@Override
	public CompletableFuture<String> respond(String systemPrompt, List<ChatEvent> context, String userMessage, String callerInfo) {
		return CompletableFuture.supplyAsync(() -> doRespond(systemPrompt, context, userMessage, callerInfo));
	}

	private String doRespond(String systemPrompt, List<ChatEvent> context, String userMessage, String callerInfo) {
		StringBuilder ctx = new StringBuilder();
		ctx.append("System: ").append(systemPrompt).append("\n\n");
		ctx.append("Context Window (chronological order, oldest to newest):\n");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss")
			.withZone(ZoneId.systemDefault());
		
		for (ChatEvent e : context) {
			// Format timestamp
			String timestamp = formatter.format(Instant.ofEpochMilli(e.getTimestampMillis()));
			
			ctx.append("[").append(timestamp).append("] ");
			ctx.append("[" + e.getKind() + "]");
			if (e.getSender() != null && !e.getSender().isEmpty()) {
				ctx.append("(" + e.getSender() + ")");
			}
			ctx.append(": ").append(e.getContent()).append('\n');
		}
		ctx.append("Caller: ").append(callerInfo);
		ctx.append("\n\nUser: ").append(userMessage);

		String response = chatModel.chat(ctx.toString());
		return response == null ? "" : response.trim();
	}
}
