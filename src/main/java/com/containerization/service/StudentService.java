package com.containerization.service;

import com.containerization.dtos.StudentRequestDTO;
import com.containerization.dtos.StudentResponseDTO;

import java.util.List;

public interface StudentService {

    StudentResponseDTO createStudent(StudentRequestDTO request);

    StudentResponseDTO getStudentById(String id);

    List<StudentResponseDTO> getAllStudents();

    StudentResponseDTO updateStudent(String id, StudentRequestDTO request);

    void deleteStudent(String id);
}