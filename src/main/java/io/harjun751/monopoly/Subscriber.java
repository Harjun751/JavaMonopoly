package io.harjun751.monopoly;

public interface Subscriber {

    void update(EventType type, Object context);

    void export();
}
