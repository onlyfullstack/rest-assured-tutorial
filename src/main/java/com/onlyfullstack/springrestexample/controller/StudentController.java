package com.onlyfullstack.springrestexample.controller;

import com.onlyfullstack.springrestexample.datatransferobject.StudentDTO;
import com.onlyfullstack.springrestexample.exception.EntityNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("students")
public class StudentController {
    @GetMapping(value = "{studentId}")
    public StudentDTO getEmployee(@PathVariable(name = "studentId") Long studentId) throws EntityNotFoundException {
        return new StudentDTO(studentId, "Elon Musk", "elonmusk@gmail.com", "pccoe");
    }

    @GetMapping
    public StudentDTO getEmployee() {
        return new StudentDTO(1l, "Elon Musk", "elonmusk@gmail.com", "pccoe");
    }
}
