package com.example.SpringBootObject.Service;

import com.example.SpringBootObject.Model.Product;
import com.example.SpringBootObject.Model.Users;
import com.example.SpringBootObject.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public void save(Product product) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userService.findByEmail(email);
        System.out.println("User Email: " + user.getEmail());
        product.setUser(user);
        productRepository.save(product);
    }

    public List<Product> getProductsByUser(Users user) {
        return productRepository.findByUser(user);
    }
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }
    public List <Product> findAll(){
        return productRepository.findAll();
    }


    @Scheduled(fixedRate = 60000)
    public void deleteOutOfStockProducts() {
        List<Product> outOfStockProducts = productRepository.findByRemaining(0);
        productRepository.deleteAll(outOfStockProducts);
    }
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }

    public void updateRemaining(Product product) {
        product.setRemaining(product.getRemaining() - 1);
        productRepository.save(product);
    }

    public List<Product> findProductByUser(Users user) {
       return productRepository.findProductByUser(user);
    }
}