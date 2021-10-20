package com.example.demo.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import com.example.demo.dto.DeleteResultDTO;
import com.example.demo.dto.SubjectDTO;
import com.example.demo.dto.UpdateResultDTO;
import com.example.demo.entity.Subject;
import com.example.demo.repository.SubjectRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    // @Autowired
    DataSource dataSource;
    SubjectRepository repo;

    @Autowired
    SubjectController(DataSource ds, SubjectRepository repo) {
        this.dataSource = ds;
        this.repo = repo;
    }

    @GetMapping("/jdbc")
    public List<SubjectDTO> AllSubject() {
        String query = "select * from tb_subject";
        List<SubjectDTO> resp = new ArrayList<>();
        Connection cn;
        try {
            cn = this.dataSource.getConnection();
            Statement stmt = cn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                Long id = res.getLong("id");
                String code = res.getString("code");
                String name = res.getString("name");
                resp.add(new SubjectDTO(id, code, name));
            }
            res.close();
            stmt.close();
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    @GetMapping()
    public List<Subject> AllTBSubject() {
        return this.repo.findAll();
    }

    @PostMapping()
    public Subject createSubject(@RequestBody SubjectDTO dto) {
        Subject sub = new Subject();
        BeanUtils.copyProperties(dto, sub);
        return this.repo.save(sub);
    }

    @PutMapping()
    public UpdateResultDTO<Subject> updateSubject(@RequestBody SubjectDTO dto) {
        UpdateResultDTO<Subject> resp = new UpdateResultDTO<>();

        if (this.repo.existsById(dto.getId())) {
            Subject sub = new Subject();
            BeanUtils.copyProperties(dto, sub);
            Subject respSub = this.repo.save(sub);
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

    @GetMapping("/find-by-code")
    public List<Subject> findByCode(@RequestParam(value = "code", required = true) String code,
            @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        return this.repo.findByCodeOrName(code, name);
    }

    @GetMapping("/feed")
    public Page<Subject> feed(Pageable page) {
        return this.repo.findAll(page);
    }
}
