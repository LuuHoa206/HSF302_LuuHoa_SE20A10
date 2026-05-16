package com.fudn.jpa_demo.service;

import com.fudn.jpa_demo.entity.Student;
import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Student createStudent(String name, String email, int age) {
        Student s = new Student(name, email, age);
        em.persist(s);    // INSERT
        System.out.println("Saved with ID = " + s.getId());
        return s;
    }

    @Transactional(readOnly = true)
    public void printAll() {
        em.createQuery("SELECT s FROM Student s", Student.class)
                .getResultList()
                .forEach(System.out::println);
    }

    @Transactional
    public void updateStudent(Long id, String name, String newEmail, int age) {
        Student s = em.find(Student.class, id);
        if (s != null) {
            s.setEmail(newEmail);
            s.setAge(age);
            s.setFullName(name);
            em.merge(s); // UPDATE
        }
    }
    @Transactional
    public void deleteStudent(Long id) {
        Student student = em.find(Student.class, id);
        if (student == null) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        em.remove(student);
        System.out.println("Deleted student with ID = " + id);
    }


}

