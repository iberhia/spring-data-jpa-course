package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//JpaRepository<T, ID> T:Entity, ID:PK Type
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     *     un pk等：optional，其他返回list，因为可能有多个
     * @param email email
     * @return Optional
     */
    Optional<Student> findStudentByEmail(String email);

    List<Student> findStudentByFirstNameEqualsAndAgeEquals(String firstName, Integer age);
}
