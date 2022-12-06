package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository<T, ID> T:Entity, ID:PK Type
public interface StudentRepository extends JpaRepository<Student, Long> {

}
