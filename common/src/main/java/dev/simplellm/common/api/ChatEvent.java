package dev.simplellm.common.api;

public final class ChatEvent {
	public enum Kind { SYSTEM, PLAYER, AI }

	private final Kind kind;
	private final String sender;
	private final String content;
	private final long timestampMillis;

	public ChatEvent(Kind kind, String sender, String content, long timestampMillis) {
		this.kind = kind;
		this.sender = sender;
		this.content = content;
		this.timestampMillis = timestampMillis;
	}

	public Kind getKind() { return kind; }
	public String getSender() { return sender; }
	public String getContent() { return content; }
	public long getTimestampMillis() { return timestampMillis; }
}
