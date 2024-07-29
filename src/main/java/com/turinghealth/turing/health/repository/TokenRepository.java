package com.turinghealth.turing.health.repository;

import com.turinghealth.turing.health.entity.meta.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
            SELECT t FROM Token t INNER JOIN User u\s
            ON t.user.id = u.id\s
            WHERE u.id = :id AND (t.expired = false OR t.revoked = false)\s
            """)
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);

}
