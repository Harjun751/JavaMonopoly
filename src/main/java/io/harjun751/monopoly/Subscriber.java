package io.harjun751.monopoly;

public interface Subscriber {

    public void update(EventType type, Object context);
}
