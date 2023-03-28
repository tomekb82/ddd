package com.example.demo.subscription;

import java.util.List;

public record Success(List<DomainEvent> events) {
}
