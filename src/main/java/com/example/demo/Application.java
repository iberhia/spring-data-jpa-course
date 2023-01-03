package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
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
            for (int i = 0; i < 1; i++) {
                Student student = Application.this.fakeStudent(faker);
                final StudentIdCard studentIdCard = new StudentIdCard(UUID.randomUUID().toString().substring(0, 15), student);
                student.addBook(new Book("Clean Code", LocalDateTime.now().minusDays(4)));
                student.addBook(new Book("DDD Principle", LocalDateTime.now().minusYears(4)));
                student.addBook(new Book("Mysql Pro", LocalDateTime.now().minusMonths(4)));
                student.setStudentIdCard(studentIdCard);
                studentRepository.save(student);
                //                    studentIdCardRepository.save(studentIdCard);
                studentRepository.findById(50L)
                        .ifPresent(s -> {
                            System.out.println(s);
                            System.out.println(s.getBooks());
                        });
            }


            studentIdCardRepository.findById(1L)
                    .ifPresent(System.out::println);

            studentRepository.deleteById(50L);
//            studentIdCardRepository.deleteById(1L);
//
//            studentIdCardRepository.findById(1L)
//                    .ifPresentOrElse(System.out::println,()-> System.out.println("\n\nstudent ID card not found!!!"));
//            studentRepository.findById(50L)
//                    .ifPresentOrElse(e -> System.out.println("Student = " + e.getId()), () -> System.out.println("\n\nstudent not found!!!"));
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
