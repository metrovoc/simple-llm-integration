package dev.simplellm.neoforge;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

final class SimpleLlmCommands {
	private SimpleLlmCommands() {}

	static void register(RegisterCommandsEvent event) {
		LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("simplellm");

		root.then(Commands.literal("trigger")
			.then(Commands.argument("value", StringArgumentType.greedyString())
				.executes(ctx -> {
					String v = StringArgumentType.getString(ctx, "value");
					SimpleLlmRuntime.setTrigger(v);
					ctx.getSource().sendSuccess(() -> net.minecraft.network.chat.Component.literal("Trigger set to: " + v), true);
					return Command.SINGLE_SUCCESS;
				})
			)
		);

		root.then(Commands.literal("prompt")
			.then(Commands.argument("value", StringArgumentType.greedyString())
				.executes(ctx -> {
					String v = StringArgumentType.getString(ctx, "value");
					SimpleLlmRuntime.setSystemPrompt(v);
					ctx.getSource().sendSuccess(() -> net.minecraft.network.chat.Component.literal("System prompt updated."), true);
					return Command.SINGLE_SUCCESS;
				})
			)
		);

		event.getDispatcher().register(root);
	}
}
