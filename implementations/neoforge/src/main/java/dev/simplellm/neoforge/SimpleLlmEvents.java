package dev.simplellm.neoforge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;

@EventBusSubscriber
public final class SimpleLlmEvents {
	private SimpleLlmEvents() {}

	@SubscribeEvent
	public static void onRegisterCommands(RegisterCommandsEvent event) {
		SimpleLlmCommands.register(event);
	}

	@SubscribeEvent
	public static void onServerChat(ServerChatEvent event) {
		String raw = event.getRawText();
		String trig = SimpleLlmRuntime.getTrigger();
		
		// Record all public messages to window first
		if (raw != null && !raw.trim().isEmpty()) {
			SimpleLlmRuntime.recordPlayer(event.getUsername(), raw);
		}
		
		// Check if message contains trigger word and handle LLM response
		if (raw != null && trig != null && !trig.isEmpty() && raw.trim().contains(trig)) {
			// Send the COMPLETE message to LLM, not just the part after trigger
			SimpleLlmRuntime.handleTriggeredMessage(event.getPlayer(), raw.trim());
		}
	}

	@SubscribeEvent
	public static void onAdvancementEarned(AdvancementEvent.AdvancementEarnEvent event) {
		String playerName = event.getEntity().getGameProfile().getName();
		String advancementTitle = event.getAdvancement().value().display()
			.map(display -> display.getTitle().getString())
			.orElse("Unknown Achievement");
		
		String message = String.format("Player %s earned advancement: %s", playerName, advancementTitle);
		SimpleLlmRuntime.recordSystem(message);
	}

	@SubscribeEvent
	public static void onAdvancementProgress(AdvancementEvent.AdvancementProgressEvent event) {
		String playerName = event.getEntity().getGameProfile().getName();
		String advancementTitle = event.getAdvancement().value().display()
			.map(display -> display.getTitle().getString())
			.orElse("Unknown Achievement");
		String criterion = event.getCriterionName();
		String progressType = event.getProgressType().name();
		
		String message = String.format("Player %s made progress on advancement '%s': %s criterion '%s'", 
			playerName, advancementTitle, progressType, criterion);
		SimpleLlmRuntime.recordSystem(message);
	}
}
