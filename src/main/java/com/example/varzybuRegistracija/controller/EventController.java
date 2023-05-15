package com.example.varzybuRegistracija.controller;

import com.example.varzybuRegistracija.dto.NewEventBody;
import com.example.varzybuRegistracija.entity.Event;
import com.example.varzybuRegistracija.service.EventService;
import com.example.varzybuRegistracija.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Secured("ROLE_ADMIN")
@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final UserService userService;

    @GetMapping("/event")
    public String startEvent(Model model) {
        Event currentEvent = eventService.getCurrentEvent();
        Long registeredCount = userService.getCountByRole("ROLE_USER");
        model.addAttribute("currentEvent", currentEvent);
        model.addAttribute("countRegistered", registeredCount);
        return "event";
    }

    @GetMapping("/newevent")
    public String newEvent(Model model) {
        NewEventBody eventBody = new NewEventBody();
        model.addAttribute("newEvent", eventBody);
        return "newevent";
    }

    @PostMapping("/newevent")
    public String createNewEvent(@ModelAttribute("newEvent") NewEventBody newEvent) {
        eventService.createNewEvent(newEvent);
        return "redirect:/";
    }
}
