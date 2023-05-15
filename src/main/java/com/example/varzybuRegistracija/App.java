package com.example.varzybuRegistracija;

import com.example.varzybuRegistracija.entity.Event;
import com.example.varzybuRegistracija.entity.Role;
import com.example.varzybuRegistracija.enums.EventStatus;
import com.example.varzybuRegistracija.repository.EventRepository;
import com.example.varzybuRegistracija.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner run(
            RoleRepository roleRepository,
            EventRepository eventRepository
    ) {
        return args -> {
            String userRoleString = "ROLE_USER";
            String adminRoleString = "ROLE_ADMIN";

            //INITIALLIZE INITIAL ROLES IF NOT EXIST

            Optional<Role> userRole = roleRepository.findByName(userRoleString);
            if (userRole.isEmpty()) {
                Role roleUser = new Role();
                roleUser.setName(userRoleString);
                roleRepository.save(roleUser);
            }

            Optional<Role> adminRole = roleRepository.findByName(adminRoleString);
            if (adminRole.isEmpty()) {
                Role roleAdmin = new Role();
                roleAdmin.setName(adminRoleString);
                roleRepository.save(roleAdmin);
            }

            //INITIALLIZE INITIAL EVENT IF NOT EXIST
            Optional<Event> activeEvent = eventRepository.findFirstByEventStatus(EventStatus.ACTIVE);
            if (activeEvent.isEmpty()) {
                Event initialEvent = new Event();
                initialEvent.setEventStatus(EventStatus.ACTIVE);
                initialEvent.setEventName("Initial Event");
                initialEvent.setEventDescription("Simple event just for starting");
                initialEvent.setEventStarted(LocalDateTime.now());
                eventRepository.save(initialEvent);
            }
        };
    }

}
