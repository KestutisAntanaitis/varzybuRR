package com.example.varzybuRegistracija.service.impl;

import com.example.varzybuRegistracija.dto.NewEventBody;
import com.example.varzybuRegistracija.entity.Event;
import com.example.varzybuRegistracija.enums.EventStatus;
import com.example.varzybuRegistracija.repository.EventRepository;
import com.example.varzybuRegistracija.service.EventService;
import com.example.varzybuRegistracija.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserService userService;

    @Override
    public Event getCurrentEvent() {
        return eventRepository.findFirstByEventStatus(EventStatus.ACTIVE).orElseThrow(() ->
                new EntityNotFoundException("Could not find active event"));
    }

    @Transactional
    @Override
    public void createNewEvent(NewEventBody event) {
        Event oldEvent = getCurrentEvent();
        oldEvent.setEventStatus(EventStatus.ENDED);
        oldEvent.setEventEnds(LocalDateTime.now());
        Event newEvent = mapFromDto(event);
        deleteAllUsers();
        eventRepository.saveAll(List.of(oldEvent, newEvent));
    }

    private void deleteAllUsers() {
        userService.deleteAllNonAdminUsers();
    }

    private Event mapFromDto(NewEventBody newEvent) {
        Event event = new Event();
        event.setEventName(newEvent.getEventName());
        event.setEventDescription(newEvent.getEventDescription());
        event.setEventStarted(LocalDateTime.now());
        event.setEventStatus(EventStatus.ACTIVE);
        return event;
    }

}
