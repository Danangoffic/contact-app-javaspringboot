package com.app.contact.controller;

import com.app.contact.model.Contact;
import com.app.contact.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@Tag(name = "Contact Management", description = "API for managing contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    @Operation(summary = "Get all contacts", description = "Retrieve a list of all contacts")
    public ResponseEntity<List<Contact>> getAllContacts(
            @Parameter(description = "Search keyword for name, email, or phone") @RequestParam(required = false) String keyword) {
        List<Contact> contacts;
        if (keyword != null && !keyword.trim().isEmpty()) {
            contacts = contactService.searchContacts(keyword);
        } else {
            contacts = contactService.getAllContacts();
        }
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get contact by ID", description = "Retrieve a contact by its ID")
    public ResponseEntity<Contact> getContactById(
            @Parameter(description = "Contact ID") @PathVariable Long id) {
        return contactService.getContactById(id)
                .map(contact -> new ResponseEntity<>(contact, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Create a new contact", description = "Create a new contact with the provided details")
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact) {
        try {
            Contact createdContact = contactService.createContact(contact);
            return new ResponseEntity<>(createdContact, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a contact", description = "Update an existing contact with the provided details")
    public ResponseEntity<Contact> updateContact(
            @Parameter(description = "Contact ID") @PathVariable Long id,
            @Valid @RequestBody Contact contactDetails) {
        try {
            Contact updatedContact = contactService.updateContact(id, contactDetails);
            return new ResponseEntity<>(updatedContact, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a contact", description = "Delete a contact by its ID")
    public ResponseEntity<Void> deleteContact(
            @Parameter(description = "Contact ID") @PathVariable Long id) {
        try {
            contactService.deleteContact(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search contacts", description = "Search contacts by keyword in name, email, or phone")
    public ResponseEntity<List<Contact>> searchContacts(
            @Parameter(description = "Search keyword for name, email, or phone") @RequestParam String keyword) {
        List<Contact> contacts = contactService.searchContacts(keyword);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }
}