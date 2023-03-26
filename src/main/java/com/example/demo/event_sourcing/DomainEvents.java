package com.example.demo.event_sourcing;

import java.util.List;

record DomainEvents(List<DomainEvent> events) { }
