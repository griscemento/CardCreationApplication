package com.ejercicio2.persistence.implementation;

import com.ejercicio2.model.User;
import org.springframework.data.repository.query.Param;

public interface IUserRepository {

    void createUser(User user);

    User findUserByCardId(@Param("cardId") Long cardId);

    void updateUser(User user);
}
