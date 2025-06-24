package com.makarimal.aisprotect_back.controller;

import com.makarimal.aisprotect_back.model.ContactMessage;
import com.makarimal.aisprotect_back.service.ContactMessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contactMessage")
@CrossOrigin(origins = "*")
public class ContactMessageController {
    @Autowired
    private final ContactMessageService contactService;

    public ContactMessageController(ContactMessageService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ContactMessage> submitContactForm(@Valid @RequestBody ContactMessage contactMessage) {
        ContactMessage savedMessage = contactService.saveContactMessage(contactMessage);
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }
}