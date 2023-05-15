package com.example.varzybuRegistracija.service;

import com.example.varzybuRegistracija.dto.NewEventBody;
import com.example.varzybuRegistracija.entity.Event;

public interface EventService {
    Event getCurrentEvent();

    void createNewEvent(NewEventBody newEvent);
}
