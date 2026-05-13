package com.fudn.demo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void createStudent(String name, String email, int age) {
        Student s = new Student(name, email, age);
        em.persist(s);      // INSERT
        System.out.println("Saved with ID = " + s.getId());
    }

    @Transactional(readOnly = true)
    public void printAll() {
        em.createQuery("SELECT s FROM Student s", Student.class)
                .getResultList()
                .forEach(System.out::println);
    }

    @Transactional
    public void updateStudent(Long id, String newName, String newEmail, Integer newAge) {
        Student student = em.find(Student.class, id);

        if (student != null) {
            student.setFullName(newName);
            student.setEmail(newEmail);
            student.setAge(newAge);

            System.out.println("Đã cập nhật thành công sinh viên có ID = " + id);
        } else {
            System.out.println("Không tìm thấy sinh viên để cập nhật với ID = " + id);
        }
    }
    @Transactional
    public void deleteStudent(Long id) {
        Student student = em.find(Student.class, id);

        if (student != null) {
            em.remove(student);
            System.out.println("Đã xóa thành công sinh viên có ID = " + id);
        } else {
            System.out.println("Không tìm thấy sinh viên với ID = " + id + " để xóa.");
        }
    }
}