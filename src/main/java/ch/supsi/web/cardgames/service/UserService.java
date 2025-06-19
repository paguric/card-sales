package ch.supsi.web.cardgames.service;

import ch.supsi.web.cardgames.model.User;
import ch.supsi.web.cardgames.model.UserRole;
import ch.supsi.web.cardgames.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public User saveUser(User user) {
        // Data in user Model is already valid, as the JavaScript already controls it
        // May add validation here anyway
        // TODO

        // Encrypt password
        PasswordEncoder pe = new BCryptPasswordEncoder();
        String encodedPassword = pe.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Set default user role, if user is not ADMIN
        if (user.getRole() == null || !user.getRole().equals(UserRole.ADMIN)) {
            user.setRole(UserRole.USER);
        }

        // Save to db
        return repo.save(user);
    }

    public User findUserByUsername(String username) {
        return repo.findByUsername(username);
    }

    public User getCurrentlyLoggedUser() {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findUserByUsername(user.getUsername());
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

}
