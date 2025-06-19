package ch.supsi.web.cardgames.controller;

import ch.supsi.web.cardgames.model.User;
import ch.supsi.web.cardgames.model.UserRole;
import ch.supsi.web.cardgames.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // If user is already logged in, he gets redirected to the home page
        if (isLoggedIn()) {
            return "redirect:/";
        }
        
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        // Try saving to db
        if (userService.saveUser(user) == null) {
            // May show a message to the user, rather than using console
            System.err.println("ERROR SAVING USER " + user);
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        // If user is already logged in, he gets redirected to the home page
        if (isLoggedIn()) {
            return "redirect:/";
        }

        return "login";
    }

    private boolean isLoggedIn() {
        // If user is not logged in, getPrincipal() returns a String instead of a User object
        return !(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String);
    }

}
