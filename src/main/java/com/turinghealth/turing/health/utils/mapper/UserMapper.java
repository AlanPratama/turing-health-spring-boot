package com.turinghealth.turing.health.utils.mapper;

import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.utils.dto.userDTO.UserResponseDTO;

public class UserMapper {
    
    public static UserResponseDTO userResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .nik(user.getNik())
                .phone(user.getPhone())
                .address(user.getAddress())
                .email(user.getEmail())
                .role(user.getRole())
                .region(user.getRegion())
                .build();
    }
    
    
}
