package com.example.varzybuRegistracija.repository;

import com.example.varzybuRegistracija.entity.Event;
import com.example.varzybuRegistracija.enums.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findFirstByEventStatus(EventStatus status);
}
