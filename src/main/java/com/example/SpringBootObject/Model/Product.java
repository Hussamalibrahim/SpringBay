package com.example.SpringBootObject.Model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    @Column(name = "remaining")
    private int remaining;

    @Column(name = "image")
    private String image = "images/ite.jpg";

    @ManyToOne
    @JoinColumn(name = "user_email", nullable = false)
    private Users user;
    @ManyToOne
    @JoinColumn(name = "user_payment")
    private Users users;
}