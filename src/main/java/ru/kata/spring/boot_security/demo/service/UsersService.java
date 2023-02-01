package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UsersService {

    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User userVerification(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            throw new RuntimeException("User kaput");
        }
        return user;
    }


    public User getById(Long  id) {
        return userRepository.getById(id);
    }
    @Transactional
    public void updateUser(User user){
      userRepository.saveAndFlush(user);

    }
}
