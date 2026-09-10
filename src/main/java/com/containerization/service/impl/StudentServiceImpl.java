package com.containerization.service.impl;

import com.containerization.dtos.StudentRequestDTO;
import com.containerization.dtos.StudentResponseDTO;
import com.containerization.entities.Student;
import com.containerization.exceptions.StudentNotFoundException;
import com.containerization.repository.StudentRepository;
import com.containerization.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentResponseDTO createStudent(StudentRequestDTO request) {

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Student with email " + request.getEmail() + " already exists"
            );
        }

        Student student = new Student();

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPassword(request.getPassword());

        Student savedStudent = studentRepository.save(student);

        return convertToResponseDTO(savedStudent);
    }

    @Override
    public StudentResponseDTO getStudentById(String id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with id: " + id
                        )
                );

        return convertToResponseDTO(student);
    }

    @Override
    public List<StudentResponseDTO> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    @Override
    public StudentResponseDTO updateStudent(
            String id,
            StudentRequestDTO request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with id: " + id
                        )
                );

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPassword(request.getPassword());

        Student updatedStudent = studentRepository.save(student);

        return convertToResponseDTO(updatedStudent);
    }

    @Override
    public void deleteStudent(String id) {

        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(
                    "Student not found with id: " + id
            );
        }

        studentRepository.deleteById(id);
    }

    private StudentResponseDTO convertToResponseDTO(Student student) {

        return new StudentResponseDTO(
                student.getStudentId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail()
        );
    }
}