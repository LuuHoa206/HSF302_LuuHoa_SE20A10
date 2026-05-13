package com.fudn.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(StudentService service) {
        return args -> {
            // 1. Tạo mới dữ liệu (Create)
            service.createStudent("Nguyễn Văn A", "a@fpt.edu.vn", 20);
            service.createStudent("Trần Thị B", "b@fpt.edu.vn", 21);

            System.out.println("\n--- DANH SÁCH TRƯỚC KHI CẬP NHẬT ---");
            service.printAll();

            System.out.println("\n--- TIẾN HÀNH CẬP NHẬT (UPDATE) ---");
            service.updateStudent(1L, "Nguyễn Văn A Updated", "a_updated@fpt.edu.vn", 25);

            System.out.println("\n--- DANH SÁCH SAU KHI CẬP NHẬT ---");
            service.printAll();
        };
    }
}