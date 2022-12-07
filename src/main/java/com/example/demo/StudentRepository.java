package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//JpaRepository<T, ID> T:Entity, ID:PK Type
public interface StudentRepository extends JpaRepository<Student, Long> {

//    /**
//     *     un pk等：optional，其他返回list，因为可能有多个
//     * @param email email
//     * @return Optional
//     */
//    Optional<Student> findStudentByEmail(String email);

    //参数从 ?1 开始，而不是 ?0
    @Query("select s from StudentEntityName s where s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    @Query("select s from StudentEntityName s where s.firstName = ?1 and s.age >= ?2")
    List<Student> findStudentByFirstNameEqualsAndAgeIsGreaterThanEqual(String firstName, Integer age);

    @Query("select s from StudentEntityName s where s.firstName = ?1 and s.age >= ?2")
        //@Query指定语句之后，可以自定义方法名字，不需要按jpa的自动生成方法名
    List<Student> selectStudentByFirstNameAndAgeGreaterEqual(String firstName, Integer age);

    @Query(value = "select * from amigoscode.student where first_name= ?1 and age>= ?2", nativeQuery = true)
    List<Student> selectStudentByFirstNameAndAgeGreaterEqualNative(String firstName, Integer age);

    @Query(value = "select * from amigoscode.student where first_name= :firstName and age>= :age", nativeQuery = true)
    List<Student> selectStudentByFirstNameAndAgeGreaterEqualNativeNamedParams(@Param("firstName") String firstName, @Param("age") Integer age);

    List<Student> findStudentByFirstNameEqualsAndAgeEquals(String firstName, Integer age);

    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("delete from StudentEntityName s where s.id = :id")
    int deleteStudentById(@Param("id") Long id);
}
