package org.stalkxk.opensourcesoftcatalog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stalkxk.opensourcesoftcatalog.dto.RegistrationUserDto;
import org.stalkxk.opensourcesoftcatalog.entity.Role;
import org.stalkxk.opensourcesoftcatalog.entity.User;
import org.stalkxk.opensourcesoftcatalog.repository.UserRepository;

import java.util.List;
import java.util.Optional;


/**
 * Сервіс, що надає методи для роботи з користувачами.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Знаходить користувача за логіном.
     *
     * @param userLogin логін користувача
     * @return об'єкт користувача, якщо він знайдений
     */
    @Transactional(readOnly = true)
    public Optional<User> findByLogin(String userLogin){
        return userRepository.findByLogin(userLogin);
    }

    /**
     * Знаходить користувача за ідентифікатором.
     *
     * @param userId ідентифікатор користувача
     * @return об'єкт користувача, якщо він знайдений
     */
    @Transactional(readOnly = true)
    public Optional<User> findById(Integer userId){
        return userRepository.findById(userId);
    }

    /**
     * Повертає список усіх користувачів.
     *
     * @return список усіх користувачів
     */
    @Transactional(readOnly = true)
    public Page<User> findAllUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    /**
     * Зберігає нового користувача в системі.
     *
     * @param registrationUserDto дані нового користувача
     * @return збережений користувач
     */
    public User saveUser(RegistrationUserDto registrationUserDto){
        Role role = roleService.findById(registrationUserDto.getRole().getRoleId()).orElseThrow();
        User user = User.builder()
                .login(registrationUserDto.getLogin())
                .password(passwordEncoder.encode(registrationUserDto.getPassword()))
                .build();
        if(findByLogin(user.getLogin()).isPresent()){
            throw new RuntimeException("Користувач існує");
        }else {
            user.addRole(role);
            return userRepository.save(user);
        }
    }

    /**
     * Видаляє користувача з системи.
     *
     * @param user об'єкт користувача, який потрібно видалити
     */
    public void removeUser(User user){
        userRepository.delete(user);
    }

    /**
     * Оновлює інформацію про користувача.
     *
     * @param updatedUser об'єкт з оновленою інформацією про користувача
     */
    public User updateUser(User updatedUser){
        User user = findById(updatedUser.getUserId()).orElseThrow();
        user.setUserId(updatedUser.getUserId());
        user.setLogin(updatedUser.getLogin());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        user.setRoles(updatedUser.getRoles());
        return userRepository.save(user);
    }

    /**
     * Завантажує дані користувача за логіном.
     *
     * @param username логін користувача
     * @return об'єкт Spring UserDetails
     * @throws UsernameNotFoundException якщо користувача не знайдено
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("Error"));
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).toList()
        );
    }
}
