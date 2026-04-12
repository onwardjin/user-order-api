package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    @OneToMany(mappedBy = "user") // FK는 Order의 user가 한다.
    private List<Order> orders = new ArrayList<>();

    public User() {}

    public User(String name, int age){
        // this.id = id (X)
        this.name = name;
        this.age = age;
    }

    public Long getId(){ return id; }
    public String getName(){ return name; }
    public int getAge(){ return age; }
    public List<Order> getOrders(){
        return orders;
    }
    public void setName(String name){ this.name = name; }
    public void setAge(int age){ this.age = age; }
}