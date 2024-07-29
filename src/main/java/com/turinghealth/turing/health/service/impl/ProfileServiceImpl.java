package com.turinghealth.turing.health.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.service.ProfileService;
import com.turinghealth.turing.health.utils.adviser.exception.AuthenticationException;
import com.turinghealth.turing.health.utils.adviser.exception.ValidateException;
import com.turinghealth.turing.health.utils.dto.profileDTO.ChangePasswordDTO;
import com.turinghealth.turing.health.utils.dto.profileDTO.UserProfileDTO;
import com.turinghealth.turing.health.utils.dto.userDTO.UserResponseDTO;
import com.turinghealth.turing.health.utils.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Cloudinary cloudinary;

    @Override
    public UserResponseDTO getInfoProfile() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

        return UserMapper.userResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateInfoProfile(UserProfileDTO request, MultipartFile multipartFile) throws IOException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

        if (multipartFile != null && !multipartFile.isEmpty()) {

            if (user.getUserImageLink() != null) {
                String oldImageLink = user.getUserImageLink();
                String oldPublicId = oldImageLink.substring(oldImageLink.lastIndexOf("/") + 1, oldImageLink.lastIndexOf("."));

                cloudinary.uploader().destroy(oldPublicId, Map.of());
            }

            File convFile = new File(multipartFile.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(multipartFile.getBytes());
            fos.close();

            Map uploadResult = cloudinary.uploader().upload(
                    convFile, Map.of("public_id", "profile" + request.getName() + "_" + UUID.randomUUID(),
                    "transformation", new Transformation().width(150).height(150).crop("fill").gravity("center")
            ));
            String newPhotoLink = uploadResult.get("url").toString();

            user.setUserImageLink(newPhotoLink);

        }

        user.setName(request.getName());
        user.setNik(request.getNik());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setEmail(request.getEmail());
        user.setRegion(request.getRegion());

        return UserMapper.userResponseDTO(userRepository.save(user));
    }

    @Override
    public UserResponseDTO changePassword(ChangePasswordDTO request) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));
        String oldPassword = request.getOldPassword();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ValidateException("Old Password Is Incorrect!");
        }

        String newPass = request.getNewPassword();
        String confirmPass = request.getConfirmPassword();

        if (!newPass.equals(confirmPass)) {
            throw new ValidateException("New Password Is Not Match!");
        }

        user.setPassword(passwordEncoder.encode(newPass));

        return UserMapper.userResponseDTO(userRepository.save(user));
    }

}
