package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import com.example.demo.dto.ContactDTO;
import com.example.demo.dto.DeleteResultDTO;
import com.example.demo.dto.UpdateResultDTO;
import com.example.demo.entity.Contact;
import com.example.demo.entity.Subject;
import com.example.demo.repository.ContactRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
public class ContactController {
    ContactRepository repo;

    @Autowired
    ContactController(ContactRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Contact> finAll() {
        return this.repo.findAll();
    }

    @PostMapping()
    public Contact createContact(@RequestBody ContactDTO dto) {
        Contact cnt = new Contact();
        BeanUtils.copyProperties(dto, cnt);
        // this.subRepo.getById(dto.g)
        Subject sub = new Subject();
        sub.setId(dto.getSubjectId());
        cnt.setSubject(sub);
        cnt.setCreateDate(new Date());
        return this.repo.save(cnt);
    }

    @PutMapping()
    public UpdateResultDTO<Contact> updateContact(@RequestBody ContactDTO dto) {
        UpdateResultDTO<Contact> resp = new UpdateResultDTO<>();

        if (this.repo.existsById(dto.getId())) {
            Contact cnt = new Contact();
            BeanUtils.copyProperties(dto, cnt);
            Subject sub = new Subject();
            sub.setId(dto.getSubjectId());
            cnt.setSubject(sub);
            cnt.setUpdateDate(new Date());
            Contact respSub = this.repo.save(cnt);
            resp.setSuccess(true);
            resp.setEffectRow(1);
            resp.setResult(respSub);
        } else {
            resp.setSuccess(false);
            resp.setEffectRow(0);
        }
        return resp;
    }

    @DeleteMapping("/{id}")
    public DeleteResultDTO deleteSubject(@PathVariable(name = "id") Long id) {
        DeleteResultDTO resp = new DeleteResultDTO();
        if (this.repo.existsById(id)) {
            this.repo.deleteById(id);
            resp.setEffectRow(1);
            resp.setSuccess(true);
        } else {
            resp.setSuccess(false);
            resp.setEffectRow(0);
            resp.setErrorMessage("NO_ID_FOUND");
        }

        return resp;
    }

}
