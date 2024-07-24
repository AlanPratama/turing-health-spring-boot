package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.utils.dto.userDTO.UserRequestDTO;
import com.turinghealth.turing.health.utils.dto.userDTO.UserResponseDTO;
import com.turinghealth.turing.health.utils.dto.userDTO.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponseDTO create(UserRequestDTO request);
    Page<UserResponseDTO> getAll(Pageable pageable, String name, Role role);
    UserResponseDTO getOne(Integer id);
    UserResponseDTO update(UserUpdateDTO request, Integer id);
    void delete(Integer id);
}
