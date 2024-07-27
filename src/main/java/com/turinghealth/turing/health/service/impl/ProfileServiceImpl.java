package com.turinghealth.turing.health.service.impl;

import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.service.ProfileService;
import com.turinghealth.turing.health.utils.dto.profileDTO.ChangePasswordDTO;
import com.turinghealth.turing.health.utils.dto.profileDTO.UserProfileDTO;
import com.turinghealth.turing.health.utils.dto.userDTO.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    // ON PROGRESS, WE NEED TO IMPLEMENT JWT AUTHENTICATION BEFORE BUILD THIS FEATURE

    @Override
    public UserResponseDTO getInfoProfile() {
        return null;
    }

    @Override
    public UserResponseDTO updateInfoProfile(UserProfileDTO requst) {
        return null;
    }

    @Override
    public UserResponseDTO changePassword(ChangePasswordDTO request) {
        return null;
    }
}
