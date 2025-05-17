package com.example.SpringBootObject.Controller;


import com.example.SpringBootObject.Model.Users;
import com.example.SpringBootObject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/myProfile")
@SuppressWarnings("unused")
public class ProfileController {
    @Autowired
    private UserService userService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping
    public String myProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Users user = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("users", user);
        return "myProfile";
    }

    @ResponseBody
    @PostMapping("/changePassword")
    public String changePassword(Model model,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmNewPassword) {
        Users user = userService.findByEmail(userDetails.getUsername());

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            model.addAttribute("errorMessage", "your password wrong");
            return "redirect:/myProfile";
        }
        if (!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("errorMessage", "New passwords do not match.");
            return "redirect:/myProfile";
        }
        user.setPassword(newPassword);
        userService.saveUser(user);
        model.addAttribute("errorMessage", "Password changed successfully!");
        return "redirect:/myProfile";
    }
}
