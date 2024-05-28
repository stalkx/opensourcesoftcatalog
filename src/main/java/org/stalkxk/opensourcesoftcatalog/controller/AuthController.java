package org.stalkxk.opensourcesoftcatalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.stalkxk.opensourcesoftcatalog.config.JwtTokenUtil;
import org.stalkxk.opensourcesoftcatalog.dto.JwtRequest;
import org.stalkxk.opensourcesoftcatalog.dto.JwtResponse;
import org.stalkxk.opensourcesoftcatalog.dto.RegistrationUserDto;
import org.stalkxk.opensourcesoftcatalog.entity.User;
import org.stalkxk.opensourcesoftcatalog.exception.AppException;
import org.stalkxk.opensourcesoftcatalog.service.UserService;

import java.util.List;

/**
 * Клас-контролер для автентифікації та реєстрації користувачів.
 * Всі запити адресуються до шляху "/api/v1/auth".
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class AuthController {

    private final UserService userService; // Сервіс користувачів
    private final JwtTokenUtil jwtTokenUtil; // Утиліта для JWT токенів
    private final AuthenticationManager authenticationManager; // Менеджер аутентифікації

    /**
     * Метод для створення JWT токену на основі отриманих даних автентифікації.
     * Адреса запиту: POST "/authenticate"
     *
     * @param authRequest об'єкт, який містить дані автентифікації
     * @return ResponseEntity з токеном та списком ролей користувача або повідомленням про невдалу аутентифікацію
     */
    @PostMapping("/authenticate")
    @CrossOrigin(origins = "http://localhost:5173/")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        } catch (BadCredentialsException badCredentialsException){
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(new AppException(HttpStatus.UNAUTHORIZED.value(), "Некоректні дані"));
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getLogin());
        String token = jwtTokenUtil.generateToken(userDetails);
        List<String> rolesList = jwtTokenUtil.getRolesFromToken(token);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(new JwtResponse(token, rolesList));
    }

    /**
     * Метод для реєстрації нового користувача.
     * Адреса запиту: POST "/register"
     *
     * @param registrationUserDto об'єкт, який містить дані нового користувача для реєстрації
     * @return ResponseEntity з повідомленням про успішну реєстрацію або про вже існуючого користувача
     */
    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:5173/")
    public ResponseEntity<?> register(@RequestBody RegistrationUserDto registrationUserDto){
        if(userService.findByLogin(registrationUserDto.getLogin()).isPresent()){
            return new ResponseEntity<>(new AppException(HttpStatus.BAD_REQUEST.value(), "Користувач уже існує!!!!"), HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(registrationUserDto);
        return ResponseEntity.ok("Успішна реєстрація");
    }

    @PostMapping("/remove")
    @CrossOrigin(origins = "http://localhost:5173/")
    public ResponseEntity<?> remove(@RequestBody User user){
        userService.removeUser(user);
        return ResponseEntity.ok("Аккаунт успішно видалений");
    }
}
