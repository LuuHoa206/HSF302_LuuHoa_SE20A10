package com.fudn.demo;

import com.fudn.demo.Student;
import com.fudn.demo.StudentService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testCreateStudent() {
        studentService.createStudent("Lưu Hòa", "hoa.luu@fpt.edu.vn", 20);

        entityManager.flush();
        entityManager.clear();

        Student savedStudent = entityManager.createQuery("SELECT s FROM Student s WHERE s.email = :email", Student.class)
                .setParameter("email", "hoa.luu@fpt.edu.vn")
                .getSingleResult();

        assertNotNull(savedStudent, "Sinh viên phải tồn tại trong CSDL sau khi Create");

        assertEquals("Lưu Hòa", savedStudent.getFullName());
        assertEquals(20, savedStudent.getAge());
    }

    @Test
    public void testDeleteStudent() {
        Student dummyStudent = new Student("Trần Thị Xóa", "xoa.tran@fpt.edu.vn", 21);
        entityManager.persist(dummyStudent);
        entityManager.flush(); // Ép lưu để lấy ID

        Long idToDelete = dummyStudent.getId(); // Lấy ID của sinh viên vừa tạo

        studentService.deleteStudent(idToDelete);

        entityManager.flush();
        entityManager.clear();

        Student deletedStudent = entityManager.find(Student.class, idToDelete);

        assertNull(deletedStudent, "Sinh viên phải biến mất khỏi CSDL sau khi gọi hàm Delete");
    }
}