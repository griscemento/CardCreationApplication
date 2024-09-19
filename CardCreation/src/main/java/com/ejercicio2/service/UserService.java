package com.ejercicio2.service;

import com.ejercicio2.model.User;
import com.ejercicio2.persistence.UserRepository;
import com.ejercicio2.persistence.implementation.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService implements IUserRepository {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUserByCardId(Long cardId) {
        return userRepository.findUserByCardId(cardId);
    }

    @Override
    public void updateUser(User user) {
        Optional<User> updatedUser = userRepository.findById(user.getId());

        if (updatedUser.isEmpty()) {
            throw new NoSuchElementException();
        }

        updatedUser.get().setDni(user.getDni());
        updatedUser.get().setName(user.getName());
        updatedUser.get().setEmail(user.getEmail());
        updatedUser.get().setDateOfBirth(user.getDateOfBirth());
        updatedUser.get().setLastName(user.getLastName());
        updatedUser.get().setCards(user.getCards());
        updatedUser.ifPresent(userRepository::save);
    }

}
