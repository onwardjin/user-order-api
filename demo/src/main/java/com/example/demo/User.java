package com.example.demo;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User{
    @Id // This field is PK.
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;

    public User() {}

    public User(String name, int age){
        // this.id = id (X)
        this.name = name;
        this.age = age;
    }

    public Long getId(){ return id; }
    public String getName(){ return name; }
    public int getAge(){ return age; }
    public void setName(String name){ this.name = name; }
    public void setAge(int age){ this.age = age; }
}