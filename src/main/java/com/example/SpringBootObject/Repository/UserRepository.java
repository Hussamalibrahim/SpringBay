package com.example.SpringBootObject.Repository;

import com.example.SpringBootObject.Model.Product;
import com.example.SpringBootObject.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Users findByEmail(String email);
}