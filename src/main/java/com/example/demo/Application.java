package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            generateRandomStudents(studentRepository);
            sorting(studentRepository);
            PageRequest pageRequest = PageRequest.of(0,
                    5,
                    Sort.by("firstName").ascending()
                            .and(Sort.by("age").descending()));
            final Page<Student> studentPage = studentRepository.findAll(pageRequest);
            System.out.println("studentPage:" + studentPage);
        };
    }

    private void sorting(StudentRepository studentRepository) {
        //firstName: java class property name
//            final Sort firstName = Sort.by(Sort.Direction.ASC, "firstName");
        final Sort firstName = Sort.by("firstName").ascending()
                .and(Sort.by("age").descending());

        studentRepository
                .findAll(firstName)
                .forEach(student -> System.out.println(student.getFirstName() + " " + student.getAge()));
    }

    private void generateRandomStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 0; i < 100; i++) {
            final String firstName = faker.name().firstName();
            final String lastName = faker.name().lastName();
            final String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            final Student student = new Student(firstName, lastName, email, faker.number().numberBetween(0, 100));
            studentRepository.save(student);
        }
    }

}
