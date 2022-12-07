package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            List<Student> students = new ArrayList<>();
            Student maria = new Student("Maria",
                    "Jones",
                    "maria.jones@amigoscode.edu",
                    21);
            Student maria2 = new Student("Maria",
                    "Jones",
                    "maria2.jones@amigoscode.edu",
                    21);
            Student maria3 = new Student("Maria",
                    "Jones",
                    "maria3.jones@amigoscode.edu",
                    22);
            Student z3 = new Student("San",
                    "Zhang",
                    "z3@zju.edu",
                    28);
            students.add(maria);
            students.add(maria2);
            students.add(maria3);
            students.add(z3);
            System.out.println("Insert all students");
            studentRepository.saveAll(students);

//            System.out.println("all students count");
//            System.out.println(studentRepository.count());
//            studentRepository.findById(51L).ifPresentOrElse(
//                    System.out::println,
//                    () -> System.out.println("Student with ID=51 not found"));
//            studentRepository.findById(2L).ifPresentOrElse(
//                    System.out::println,
//                    () -> System.out.println("Student with ID=2 not found"));
//            final List<Student> all = studentRepository.findAll();
//            System.out.println("all students");
//            all.forEach(System.out::println);
//            System.out.println("delete maria");
//            studentRepository.deleteById(50L);
//            System.out.println("Student count = " + studentRepository.count());

            studentRepository
                    .findStudentByEmail("z3@zju.edu")
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("Student with email z3@zju.edu Not found"));

            studentRepository
                    .findStudentByFirstNameEqualsAndAgeEquals("maria", 21)
                    .forEach(System.out::println);

            studentRepository
                    .findStudentByFirstNameEqualsAndAgeIsGreaterThanEqual("maria", 21)
                    .forEach(System.out::println);

            studentRepository
                    .selectStudentByFirstNameAndAgeGreaterEqualNative("maria", 21)
                    .forEach(System.out::println);

            studentRepository
                    .selectStudentByFirstNameAndAgeGreaterEqualNativeNamedParams("maria", 21)
                    .forEach(System.out::println);

            System.out.println("delete maria2");

            System.out.println("student deleted count: " + studentRepository.deleteStudentById(51L));

            studentRepository
                    .selectStudentByFirstNameAndAgeGreaterEqualNativeNamedParams("maria", 21)
                    .forEach(System.out::println);

        };
    }

}
