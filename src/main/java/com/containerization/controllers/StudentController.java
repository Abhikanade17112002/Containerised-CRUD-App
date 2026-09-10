package com.containerization.controllers;

import com.containerization.dtos.StudentRequestDTO;
import com.containerization.dtos.StudentResponseDTO;
import com.containerization.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(
            @Valid @RequestBody StudentRequestDTO request) {

        StudentResponseDTO response =
                studentService.createStudent(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(
            @PathVariable String id) {

        StudentResponseDTO response =
                studentService.getStudentById(id);

        return ResponseEntity.ok(response);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {

        List<StudentResponseDTO> response =
                studentService.getAllStudents();

        return ResponseEntity.ok(response);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @PathVariable String id,
            @Valid @RequestBody StudentRequestDTO request) {

        StudentResponseDTO response =
                studentService.updateStudent(id, request);

        return ResponseEntity.ok(response);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable String id) {

        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }
}