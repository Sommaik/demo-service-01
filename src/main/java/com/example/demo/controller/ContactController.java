package com.example.demo.controller;

import java.util.List;

import com.example.demo.entity.Contact;
import com.example.demo.repository.ContactRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
public class ContactController {
    ContactRepository cntRepo;

    @Autowired
    ContactController(ContactRepository cntRepo) {
        this.cntRepo = cntRepo;
    }

    @GetMapping
    public List<Contact> finAll() {
        return this.cntRepo.findAll();
    }

}
