package com.turinghealth.turing.health.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.entity.meta.Region;
import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.middleware.UserMiddleware;
import com.turinghealth.turing.health.repository.RegionRepository;
import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.service.UserService;
import com.turinghealth.turing.health.utils.adviser.exception.AuthenticationException;
import com.turinghealth.turing.health.utils.adviser.exception.NotFoundException;
import com.turinghealth.turing.health.utils.dto.userDTO.UserRequestDTO;
import com.turinghealth.turing.health.utils.dto.userDTO.UserResponseDTO;
import com.turinghealth.turing.health.utils.dto.userDTO.UserUpdateDTO;
import com.turinghealth.turing.health.utils.mapper.UserMapper;
import com.turinghealth.turing.health.utils.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final PasswordEncoder passwordEncoder;
    private final Cloudinary cloudinary;

//    @Override
//    public UserResponseDTO create(UserRequestDTO request, MultipartFile multipartFile) throws IOException {
//        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        final User user = userRepository.findByEmail(authentication.getName())
//                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));
//
//        UserMiddleware.isAdmin(user.getRole());
//
//        Region region = regionRepository.findById(request.getRegionId()).orElseThrow(() -> new NotFoundException("Region With ID " + request.getRegionId() + " Is Not Found!"));
//
//        File convFile = new File( multipartFile.getOriginalFilename() );
//        FileOutputStream fos = new FileOutputStream( convFile );
//        fos.write( multipartFile.getBytes() );
//        fos.close();
//
//        String photo = cloudinary.uploader()
//                .upload(convFile, Map.of("public_id", "profile" + request.getName() + "_" + UUID.randomUUID(),
//                        "transformation", new Transformation().width(150).height(150).crop("fill").gravity("center")
//                ))
//                .get("url")
//                .toString();
//
//        //delete local photo so only upload via cloudinary
//        convFile.delete();
//
//        User user = User.builder()
//                .name(request.getName())
//                .nik(request.getNik())
//                .phone(request.getPhone())
//                .address(request.getAddress())
//                .userImageLink(photo)
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.valueOf(request.getRole()))
//                .region(region)
//                .build();
//
//        User createdUser = userRepository.save(user);
//
//        return UserMapper.userResponseDTO(createdUser);
//    }

    @Override
    public Page<UserResponseDTO> getAll(Pageable pageable, String name, Role role) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

        UserMiddleware.isAdmin(user.getRole());

        Specification<User> spec = UserSpecification.getSpecification(name, role);
        Page<User> tempUser = userRepository.findAll(spec, pageable);
        
        List<UserResponseDTO> list = tempUser.stream()
                .map(UserMapper::userResponseDTO)
                .collect(Collectors.toList());
                
        return new PageImpl<>(list, pageable, tempUser.getNumberOfElements());
    }

    @Override
    public UserResponseDTO getOne(Integer id) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User userAuth = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

        UserMiddleware.isAdmin(userAuth.getRole());

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User With ID " + id + " Is Not Found!"));
        
        return UserMapper.userResponseDTO(user);
    }

    @Override
    public UserResponseDTO update(UserUpdateDTO request, Integer id, MultipartFile multipartFile) throws IOException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User userAuth = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

        UserMiddleware.isAdmin(userAuth.getRole());

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User With ID " + id + " Is Not Found!"));
        Region region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new NotFoundException("Region With ID " + request.getRegionId() + " Is Not Found!"));

        if (user.getUserImageLink() != null && multipartFile != null && !multipartFile.isEmpty()) {
            String oldImageLink = user.getUserImageLink();
            String oldPublicId = oldImageLink.substring(oldImageLink.lastIndexOf('/') + 1, oldImageLink.lastIndexOf('.'));

            cloudinary.uploader().destroy(oldPublicId, Map.of());

            File convFile = new File(multipartFile.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(multipartFile.getBytes());
            fos.close();

            Map uploadResult = cloudinary.uploader().upload(convFile, Map.of("public_id", "profile" + request.getName() + "_" + UUID.randomUUID(),
                    "transformation", new Transformation().width(150).height(150).crop("fill").gravity("center")
            ));
            String newPhotoLink = uploadResult.get("url").toString();

            //delete local photo so only upload via cloudinary
            convFile.delete();

            // Update the user's image link
            user.setUserImageLink(newPhotoLink);
        } else {
            File convFile = new File( multipartFile.getOriginalFilename() );
            FileOutputStream fos = new FileOutputStream( convFile );
            fos.write( multipartFile.getBytes() );
            fos.close();

            String photo = cloudinary.uploader()
                    .upload(convFile, Map.of("profile"+request.getName(), UUID.randomUUID().toString()))
                    .get("url")
                    .toString();

            user.setUserImageLink(photo);
        }

        // Update other user details
        user.setName(request.getName());
        user.setNik(request.getNik());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setEmail(request.getEmail());
        user.setRegion(region);
        user.setRole(request.getRole());

        User updatedUser = userRepository.save(user);

        return UserMapper.userResponseDTO(updatedUser);
    }


    @Override
    public void delete(Integer id) throws IOException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User userAuth = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

        UserMiddleware.isAdmin(userAuth.getRole());

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User With ID " + id + " Is Not Found!"));

        if (!user.getUserImageLink().isEmpty()) {
            String oldImageLink = user.getUserImageLink();
            String oldPublicId = oldImageLink.substring(oldImageLink.lastIndexOf('/') + 1, oldImageLink.lastIndexOf('.'));

            cloudinary.uploader().destroy(oldPublicId, Map.of());
        }

        userRepository.delete(user);
    }
}
