package com.docker.docker.controller;
import com.docker.docker.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    List<Student> students=new ArrayList<>();
    @GetMapping("/students")
    public List<Student> getStudent(){
        return students;

    }
    @PostMapping("/add")
    public boolean getStudent(@RequestBody Student student){
        return students.add(student);

    }



}
