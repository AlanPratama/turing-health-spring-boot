package com.turinghealth.turing.health.service.impl;

import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.entity.meta.Region;
import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.repository.RegionRepository;
import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.service.UserService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserResponseDTO create(UserRequestDTO request) {
        Region region = regionRepository.findById(request.getRegionId()).orElseThrow(() -> new NotFoundException("Region With ID " + request.getRegionId() + " Is Not Found!"));
        
        User user = User.builder()
                .name(request.getName())        
                .nik(request.getNik())
                .phone(request.getPhone())
                .address(request.getAddress())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .region(region)
                .build();
        
        User createdUser = userRepository.save(user);
        
        return UserMapper.userResponseDTO(createdUser);
    }

    @Override
    public Page<UserResponseDTO> getAll(Pageable pageable, String name, Role role) {
        Specification<User> spec = UserSpecification.getSpecification(name, role);
        Page<User> tempUser = userRepository.findAll(spec, pageable);
        
        List<UserResponseDTO> list = tempUser.stream()
                .map(UserMapper::userResponseDTO)
                .collect(Collectors.toList());
                
        return new PageImpl<>(list, pageable, tempUser.getNumberOfElements());
    }

    @Override
    public UserResponseDTO getOne(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User With ID " + id + " Is Not Found!"));
        
        return UserMapper.userResponseDTO(user);
    }

    @Override
    public UserResponseDTO update(UserUpdateDTO request, Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User With ID " + id + " Is Not Found!"));
        Region region = regionRepository.findById(request.getRegionId()).orElseThrow(() -> new NotFoundException("Region With ID " + request.getRegionId() + " Is Not Found!"));
        
                
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
    public void delete(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User With ID " + id + " Is Not Found!"));
        
        userRepository.delete(user);
    }
}
