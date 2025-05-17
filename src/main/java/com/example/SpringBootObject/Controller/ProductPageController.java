package com.example.SpringBootObject.Controller;

import com.example.SpringBootObject.Model.Product;
import com.example.SpringBootObject.Model.Users;
import com.example.SpringBootObject.Service.ProductService;
import com.example.SpringBootObject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/product")
public class ProductPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    // Get all products from all users
    @GetMapping
    public String showProduct(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Users user = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("users", user);
        model.addAttribute("products", productService.findAll());
        return "productPage";
    }

    // Get all products that the user added to the website
    @GetMapping("/getMine")
    public String showMyProduct(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Users user = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("users", user);
        model.addAttribute("products", productService.getProductsByUser(user));
        return "productPage";
    }

    // Search products by name
    @GetMapping("/search")
    public String searchProducts(@RequestParam String query, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Users user = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("users", user);
        model.addAttribute("products", productService.searchProducts(query));
        return "productPage";
    }

    // When clicking on "Add Product," go to the add product page
    @GetMapping("/post")
    public String addProductPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Users user = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("product", new Product());
        return "addProductPage";
    }

    // Add product to the database and set the user who added it
    @PostMapping("/post")
    public String addProduct(@ModelAttribute("product") Product product, @AuthenticationPrincipal UserDetails userDetails) {
        Users user = userService.findByEmail(userDetails.getUsername());
        product.setUser(user);
        productService.save(product);
        return "redirect:/product";
    }

    // Display product details
    @GetMapping("/Details/{id}")
    public String productDetails(@PathVariable int id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Users user = userService.findByEmail(userDetails.getUsername());
        Product product = productService.getProductById(id);
        model.addAttribute("users", user);
        model.addAttribute("product", product);
        return "productDetails";
    }

    // Add a product to the user's payment list
    @GetMapping("/post/pyment/{productId}")
    public String addPayment(@PathVariable("productId") int productId, @AuthenticationPrincipal UserDetails userDetails) {
        Users user = userService.findByEmail(userDetails.getUsername());
        Product product = productService.getProductById(productId);
        productService.updateRemaining(product);
      user.setPayment(productService.findProductByUser(user));
        return "redirect:/product";
    }

    // Get the user's payment history
    @GetMapping("/getMine/pyment")
    public String getMyPyment(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Users user = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("users", user);
        model.addAttribute("products", productService.findProductByUser(user));
        return "productPage";
    }
}