package com.example.demo.controller;

import java.util.List;

import com.example.demo.dto.TodoDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TodoController {

    @GetMapping("/todos")
    List<TodoDTO> getTodos() throws Exception {
        RestTemplate rst = new RestTemplate();
        ResponseEntity<String> resp = rst.getForEntity("http://jsonplaceholder.typicode.com/todos", String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<TodoDTO> list = mapper.readValue(resp.getBody(), new TypeReference<List<TodoDTO>>() {
        });
        return list;
    }
}
