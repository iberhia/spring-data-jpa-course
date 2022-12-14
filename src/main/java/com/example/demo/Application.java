package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.UUID;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                        StudentIdCardRepository studentIdCardRepository) {
        Faker faker = new Faker();
        return args -> {
            for (int i = 0; i < 100; i++) {
                final StudentIdCard studentIdCard = new StudentIdCard(UUID.randomUUID().toString().substring(0,15), fakeStudent(faker));
                studentIdCardRepository.save(studentIdCard);
            }
            studentIdCardRepository.findById(1L)
                    .ifPresent(System.out::println);

            studentRepository.findById(50L)
                    .ifPresent(System.out::println);
        };
    }

    private void paging(StudentRepository studentRepository) {
        PageRequest pageRequest = PageRequest.of(0,
                5,
                Sort.by("firstName").ascending()
                        .and(Sort.by("age").descending()));
        final Page<Student> studentPage = studentRepository.findAll(pageRequest);
        System.out.println("studentPage:" + studentPage);
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

    private void generateRandomStudents(StudentRepository studentRepository, Faker faker) {
        for (int i = 0; i < 100; i++) {
            final Student student = fakeStudent(faker);
            studentRepository.save(student);
        }
    }

    private Student fakeStudent(Faker faker) {
        final String firstName = faker.name().firstName();
        final String lastName = faker.name().lastName();
        final String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
        return new Student(firstName, lastName, email, faker.number().numberBetween(0, 100));
    }

}
