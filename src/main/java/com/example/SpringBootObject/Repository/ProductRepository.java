package com.example.SpringBootObject.Repository;

import com.example.SpringBootObject.Model.Product;
import com.example.SpringBootObject.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByUser(Users user);

    List<Product> findByRemaining(int i);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findProductByUser(Users user);
}