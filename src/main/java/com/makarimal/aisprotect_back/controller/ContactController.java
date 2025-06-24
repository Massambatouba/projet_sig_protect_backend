package com.makarimal.aisprotect_back.controller;

import com.makarimal.aisprotect_back.model.Contact;
import com.makarimal.aisprotect_back.model.Testimonial;
import com.makarimal.aisprotect_back.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/contact")
@ControllerAdvice
@CrossOrigin(origins = "*")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    // API pour soumettre le formulaire de contact
    @PostMapping
    public ResponseEntity<String> submitContactForm(@RequestBody Contact contact) {
        try {
            contact.setRead(false);
            contactService.handleContactForm(contact);
            return ResponseEntity.status(HttpStatus.CREATED).body("Formulaire soumis avec succès");
        } catch (Exception e) {
            e.printStackTrace(); // Affiche l'erreur complète dans la console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du traitement du formulaire: " + e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<Contact>> getActiveTestimonials() {
        List<Contact> contacts = contactService.getAllContact();

        return ResponseEntity.ok(contacts);
    }

    @PatchMapping("/{id}/mark-read")
    public ResponseEntity<Contact> markAsRead(@PathVariable Long id, @RequestParam boolean read) {
        Contact updatedMessage = contactService.markMessageAsRead(id, read);
        return ResponseEntity.ok(updatedMessage);
    }




}
