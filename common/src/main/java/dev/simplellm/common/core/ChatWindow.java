package dev.simplellm.common.core;

import dev.simplellm.common.api.ChatEvent;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ChatWindow {
	private final int maxEvents;
	private final Deque<ChatEvent> events;

	public ChatWindow(int maxEvents) {
		this.maxEvents = Math.max(1, maxEvents);
		this.events = new ArrayDeque<>(this.maxEvents);
	}

	public synchronized void add(ChatEvent event) {
		if (events.size() >= maxEvents) {
			events.removeFirst();
		}
		events.addLast(event);
	}

	public synchronized List<ChatEvent> snapshot() {
		return new ArrayList<>(events);
	}
}
