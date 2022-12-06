package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static javax.persistence.GenerationType.SEQUENCE;

//使用java
@Entity(name = "Student")
//自定义sequence generator：主键生成器，@Target({TYPE, METHOD, FIELD})
@SequenceGenerator(
        //表示该表主键生成策略名称，它被引用在@GeneratedValue中设置的“gernerator”值中
        name = "generator_name",
        //表示生成策略用到的数据库序列名称
        sequenceName = "sequence_name",
        //每次主键值增加的大小
        allocationSize = 1,
        //表示主键初始值，默认为1
        initialValue = 50
)
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(name = "student_email_unique", columnNames = {"email"})
        }
)
public class Student {

    //Primary Key
    @Id

    @GeneratedValue(
            strategy = SEQUENCE,
            //引用的generator
            generator = "generator_name"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
            name = "email",
            nullable = false
    )
    private String email;

    @Column(
            name = "age",
            nullable = false
    )
    private Integer age;

    public Student(Long id,
                   String firstName,
                   String lastName,
                   String email,
                   Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public Student() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
