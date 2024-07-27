package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.utils.dto.profileDTO.ChangePasswordDTO;
import com.turinghealth.turing.health.utils.dto.profileDTO.UserProfileDTO;
import com.turinghealth.turing.health.utils.dto.userDTO.UserResponseDTO;

public interface ProfileService {

    UserResponseDTO getInfoProfile();
    UserResponseDTO updateInfoProfile(UserProfileDTO requst);
    UserResponseDTO changePassword(ChangePasswordDTO request);

}
