package com.example.demo.event_sourcing;

public interface CommandHandler<T> {
    void handle(T command);
}
