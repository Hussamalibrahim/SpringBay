package com.example.SpringBootObject.Controller;

import com.example.SpringBootObject.Model.Users;
import com.example.SpringBootObject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@SuppressWarnings("unused")
@Controller
public class LogController {

    @Autowired
    private UserService userService;

    // Display the login page
    @GetMapping("/loginPage")
    public String loginPage(Model model) {
        model.addAttribute("users", new Users());
        return "loginPage";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "redirect:/productPage";
    }

    // Display the register Page
    @GetMapping("/registerPage")
    public String registerPage(Model model) {
        model.addAttribute("users", new Users());
        return "registerPage";
    }

    // Add user to database and go back to login Page
    @PostMapping("/register")
    public String register(Model model, @ModelAttribute("users") Users users) {
        if (userService.findByEmail(users.getEmail()) != null) {
            model.addAttribute("errorMessage", "User already exists!");
            return "registerPage";
        }
        userService.saveUser(users);
        return "redirect:/loginPage";
    }
    // Logout
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/loginPage";
    }
}
