package dev.simplellm.neoforge;

import dev.simplellm.common.api.ChatEvent;
import dev.simplellm.common.core.ChatWindow;
import dev.simplellm.common.core.LlmService;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

final class SimpleLlmRuntime {
	private static final AtomicReference<String> trigger = new AtomicReference<>("@ui");
	private static final AtomicReference<String> systemPrompt = new AtomicReference<>("You are a helpful assistant.");
	private static final AtomicReference<ChatWindow> chatWindow = new AtomicReference<>(new ChatWindow(50));
	private static final AtomicReference<LlmService> service = new AtomicReference<>(null);

	private SimpleLlmRuntime() {}

	static String getTrigger() { return trigger.get(); }
	static void setTrigger(String t) { if (t != null && !t.isEmpty()) trigger.set(t); }

	static String getSystemPrompt() { return systemPrompt.get(); }
	static void setSystemPrompt(String p) { if (p != null && !p.isEmpty()) systemPrompt.set(p); }

	static void setService(LlmService s) { service.set(s); }

	static void updateFromConfig(dev.simplellm.common.config.LlmConfig config) {
		setTrigger(config.getTrigger());
		setSystemPrompt(config.getSystemPrompt());
		chatWindow.set(new ChatWindow(config.getContextWindow()));
	}

	static void recordSystem(String text) {
		chatWindow.get().add(new ChatEvent(ChatEvent.Kind.SYSTEM, "system", text, System.currentTimeMillis()));
	}

	static void recordPlayer(String playerName, String text) {
		chatWindow.get().add(new ChatEvent(ChatEvent.Kind.PLAYER, playerName, text, System.currentTimeMillis()));
	}

	static void recordAi(String text) {
		chatWindow.get().add(new ChatEvent(ChatEvent.Kind.AI, "ai", text, System.currentTimeMillis()));
	}

	static List<ChatEvent> snapshot() { return chatWindow.get().snapshot(); }

	static void handleTriggeredMessage(ServerPlayer sender, String content) {
		LlmService llm = service.get();
		if (llm == null) {
			 sender.sendSystemMessage(Component.literal("[simplellm] LLM not configured"));
			return;
		}
		recordPlayer(sender.getGameProfile().getName(), content);
		String callerInfo = "player=" + sender.getGameProfile().getName() + ", dim=" + sender.level().dimension().location();
		llm.respond(getSystemPrompt(), snapshot(), content, callerInfo).whenComplete((resp, err) -> {
			MinecraftServer server = sender.server;
			server.execute(() -> {
				if (err != null) {
					SimpleLlmMod.LOGGER.error("LLM error", err);
					sender.sendSystemMessage(Component.literal("[simplellm] Error: " + err.getMessage()));
					return;
				}
				String text = resp == null ? "" : resp.trim();
				if (!text.isEmpty()) {
					recordAi(text);
					sender.sendSystemMessage(Component.literal("[AI] " + text));
				}
			});
		});
	}
}
