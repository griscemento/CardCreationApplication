package com.ejercicio2.persistence;

import com.ejercicio2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN u.cards c WHERE c.id = :cardId")
    User findUserByCardId(@Param("cardId") Long cardId);

}
