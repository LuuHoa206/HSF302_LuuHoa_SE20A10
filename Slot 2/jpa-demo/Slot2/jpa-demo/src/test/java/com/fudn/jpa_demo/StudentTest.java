package com.fudn.jpa_demo;

import com.fudn.jpa_demo.entity.Student;
import com.fudn.jpa_demo.service.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class StudentTest {


    @Autowired
    private StudentService studentService;

    @PersistenceContext
    private EntityManager entityManager;


    @Test
    public void testCreateAndRetrieveStudentFromDatabase() {
        // Tạo student mới
        Student newStudent = studentService.createStudent("Test Student", "test@fpt.edu.vn", 25);
        Long id = newStudent.getId();
        entityManager.flush();
        entityManager.clear();

        // Lấy student với ID vừa tạo từ database
        Student retrievedStudent = entityManager.find(Student.class, id);

        assertNotNull(retrievedStudent, "Student should exist in database");
        assertEquals(id, retrievedStudent.getId());
        assertEquals("Test Student", retrievedStudent.getFullName());
        assertEquals("test@fpt.edu.vn", retrievedStudent.getEmail());
        assertEquals(25, retrievedStudent.getAge());
    }
    @Test
    void testDeleteStudent_Success() {
        // Tạo student
        Student newStudent = studentService.createStudent("Test Delete", "delete@fpt.edu.vn", 20);
        Long id = newStudent.getId();
        entityManager.flush();

        // Xóa student
        studentService.deleteStudent(id);
        entityManager.flush();
        entityManager.clear();

        // Verify student đã bị xóa
        Student deletedStudent = entityManager.find(Student.class, id);
        assertNull(deletedStudent);
    }

    @Test
    void testDeleteStudent_NotFound() {
        // Test xóa student không tồn tại
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.deleteStudent(999L);
        });

        assertEquals("Student not found with id: 999", exception.getMessage());
    }
}

