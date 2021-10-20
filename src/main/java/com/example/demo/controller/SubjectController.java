package com.example.demo.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import com.example.demo.dto.SubjectDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SubjectController {

    // @Autowired
    DataSource dataSource;

    @Autowired
    SubjectController(DataSource ds) {
        this.dataSource = ds;
    }

    @GetMapping("/subjects")
    public List<SubjectDTO> AllSubject() {
        String query = "select * from tb_subject";
        List<SubjectDTO> resp = new ArrayList<>();
        Connection cn;
        try {
            cn = this.dataSource.getConnection();
            Statement stmt = cn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                int id = res.getInt("id");
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
}
