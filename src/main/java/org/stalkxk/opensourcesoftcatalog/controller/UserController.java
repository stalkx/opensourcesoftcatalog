package org.stalkxk.opensourcesoftcatalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.stalkxk.opensourcesoftcatalog.entity.Program;
import org.stalkxk.opensourcesoftcatalog.entity.User;
import org.stalkxk.opensourcesoftcatalog.service.UserService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<User>> findAllUser(Pageable pageable){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.findAllUsers(pageable));
    }

    @GetMapping("/search/{login}")
    public ResponseEntity<Page<User>> getAllProgramByTitle(@PathVariable String login, Pageable pageable){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.findAllByLogin(login,pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findAllUser(@PathVariable Integer id){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.findById(id).orElseThrow());
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.updateUser(user));
    }

    @PostMapping("/update-password")
    public ResponseEntity<User> updateUserPassword(@RequestBody User user){
        System.out.println(user.getRoles());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userService.updateUserPassword(user));
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestBody User user){
        userService.removeUser(user);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(Map.of("message", "Дані успашно видалені"));
    }

    @GetMapping("/user-info")
    public ResponseEntity<User> getUserInfo() {
        // Отримання поточного користувача
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Перевірка типу автентифікації
        if (authentication instanceof Principal) {
            Principal principal = (Principal) authentication;

            // Отримання імені користувача
            String username = principal.getName();

            // Завантаження користувача з бази даних за його ім'ям
            User user = userService.findByLogin(username).orElseThrow();

            // Повернення інформації про користувача
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
