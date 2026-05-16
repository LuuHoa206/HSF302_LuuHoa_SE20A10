package com.fudn.jpa_demo;

import com.fudn.jpa_demo.service.StudentService;
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
            service.createStudent("Nguyễn Văn A", "a@fpt.edu.vn", 20);
            service.createStudent("Trần Thị B", "b@fpt.edu.vn", 21);
            service.printAll();


            // Test update student với ID = 1
            service.updateStudent(1L, "Nguyễn Văn A Updated", "a.updated@fpt.edu.vn", 22);

            System.out.println("\nAfter update:");
            service.printAll();
        };
    }
}
 