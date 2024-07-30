package com.turinghealth.turing.health.middleware;

import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.utils.adviser.exception.AuthenticationException;
import com.turinghealth.turing.health.utils.adviser.exception.ValidateException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class UserMiddleware {
    private final UserRepository userRepository;

    public static void isAdmin(Role role) {
        if (role != Role.ADMIN) throw new ValidateException("Invalid Role!");
    }

    public static void isDoctor(Role role) {
        if (role != Role.DOCTOR) throw new ValidateException("Invalid Role!");
    }

    public static void isUser(Role role) {
        if (role != Role.MEMBER) throw new ValidateException("Invalid Role!");
    }
}
