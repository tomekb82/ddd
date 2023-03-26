package com.example.demo.event_sourcing;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

class EventMapper {

    BiMap<String, Class> events = HashBiMap.create();
    {
        events.put("ProductCreated", ProductCreated.class);
        events.put("PriceUpdated", PriceUpdated.class);
    }

}
