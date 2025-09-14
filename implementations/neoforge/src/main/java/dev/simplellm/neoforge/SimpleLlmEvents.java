package dev.simplellm.neoforge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
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
		if (raw != null && trig != null && !trig.isEmpty() && raw.trim().startsWith(trig)) {
			String content = raw.trim().substring(trig.length()).trim();
			SimpleLlmRuntime.handleTriggeredMessage(event.getPlayer(), content);
		}
		// Record all public messages to window
		if (raw != null && !raw.trim().isEmpty()) {
			SimpleLlmRuntime.recordPlayer(event.getUsername(), raw);
		}
	}
}
