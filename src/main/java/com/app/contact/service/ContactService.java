package com.app.contact.service;

import com.app.contact.model.Contact;
import com.app.contact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    public Optional<Contact> getContactByEmail(String email) {
        return contactRepository.findByEmail(email);
    }

    public Contact createContact(Contact contact) {
        if (contactRepository.existsByEmail(contact.getEmail())) {
            throw new RuntimeException("Contact with email " + contact.getEmail() + " already exists");
        }
        return contactRepository.save(contact);
    }

    public Contact updateContact(Long id, Contact contactDetails) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id " + id));

        // Check if email is being updated and if it already exists for another contact
        if (!contact.getEmail().equals(contactDetails.getEmail()) && 
            contactRepository.existsByEmail(contactDetails.getEmail())) {
            throw new RuntimeException("Contact with email " + contactDetails.getEmail() + " already exists");
        }

        contact.setName(contactDetails.getName());
        contact.setEmail(contactDetails.getEmail());
        contact.setPhone(contactDetails.getPhone());
        contact.setAddress(contactDetails.getAddress());

        return contactRepository.save(contact);
    }

    public void deleteContact(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id " + id));
        contactRepository.delete(contact);
    }

    public List<Contact> searchContacts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return contactRepository.findAll();
        }
        return contactRepository.findByKeyword(keyword);
    }
}