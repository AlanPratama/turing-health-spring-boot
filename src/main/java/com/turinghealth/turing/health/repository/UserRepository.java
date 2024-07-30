package com.turinghealth.turing.health.repository;

import com.turinghealth.turing.health.entity.meta.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
        @EntityGraph(attributePaths = "users")
        Optional<User> findByEmail(String name);
}
