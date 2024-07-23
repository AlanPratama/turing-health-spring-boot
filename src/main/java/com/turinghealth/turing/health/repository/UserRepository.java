package com.turinghealth.turing.health.repository;

import com.turinghealth.turing.health.entity.meta.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
