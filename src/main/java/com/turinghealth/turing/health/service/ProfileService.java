package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.utils.dto.profileDTO.ChangePasswordDTO;
import com.turinghealth.turing.health.utils.dto.profileDTO.UserProfileDTO;
import com.turinghealth.turing.health.utils.dto.userDTO.UserResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfileService {

    UserResponseDTO getInfoProfile();
    UserResponseDTO updateInfoProfile(UserProfileDTO requst, MultipartFile multipartFile) throws IOException;
    UserResponseDTO changePassword(ChangePasswordDTO request);

}
