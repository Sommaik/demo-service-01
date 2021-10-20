package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Subject;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    public List<Subject> findByCodeOrderByCodeDesc(String code);

    public List<Subject> findByCodeOrName(String code, String name);

}
