package com.example.demo.event_sourcing;

import lombok.AllArgsConstructor;

@AllArgsConstructor
abstract class Command {
    AggregateId aggregateId;

}


