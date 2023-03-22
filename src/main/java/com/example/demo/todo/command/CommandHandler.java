package com.example.demo.todo.command;

public interface CommandHandler<T> {
    void handle(T command);
}
