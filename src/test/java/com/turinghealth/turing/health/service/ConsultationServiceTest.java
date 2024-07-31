package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.entity.meta.Consultation;
import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.middleware.UserMiddleware;
import com.turinghealth.turing.health.repository.ConsultationRepository;
import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.service.impl.ConsultationServiceImpl;
import com.turinghealth.turing.health.utils.dto.consultationDTO.ConsultationAcceptRequestDTO;
import com.turinghealth.turing.health.utils.dto.consultationDTO.ConsultationDTO;
import com.turinghealth.turing.health.utils.dto.consultationDTO.ConsultationRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConsultationServiceTest {

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private UserMiddleware userMiddleware;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ConsultationServiceImpl consultationService;

    private User user;
    private User userDoctor;

    private Consultation consultation;
    private ConsultationRequestDTO consultationRequestDTO;
    private ConsultationAcceptRequestDTO consultationAcceptRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
            user.setId(1);
            user.setName("test");
            user.setEmail("test@gmail.com");
            user.setRole(Role.MEMBER);

        SecurityContextHolder.setContext(securityContext);

        userDoctor = new User();
            userDoctor.setId(1);
            userDoctor.setName("doctor");
            userDoctor.setEmail("doctor@gmail.com");
            userDoctor.setRole(Role.DOCTOR);

        consultation = new Consultation();
            consultation.setId(1);
            consultation.setMember(user);
            consultation.setDoctor(userDoctor);
            consultation.setConsultationUrl("");
            consultation.setAccepted(false);
            consultation.setConsultationDate(Date.valueOf(LocalDate.now()));

        consultationRequestDTO = new ConsultationRequestDTO();
            consultationRequestDTO.setConsultationDate(Date.valueOf(LocalDate.now()));
            consultationRequestDTO.setDoctorId(userDoctor.getId());

        consultationAcceptRequestDTO = new ConsultationAcceptRequestDTO();
            consultationAcceptRequestDTO.setConsultationUrl("testurl");

        Consultation acceptedConsultation = new Consultation();
            acceptedConsultation.setId(1);
            acceptedConsultation.setConsultationDate(Date.valueOf(LocalDate.now()));
            acceptedConsultation.setConsultationUrl("testurl");
            acceptedConsultation.setAccepted(true);
            acceptedConsultation.setMember(user);
            acceptedConsultation.setDoctor(userDoctor);

        when(consultationRepository.save(any(Consultation.class))).thenReturn(acceptedConsultation);
    }

    @Test
    void startConsultationMember_Success() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.findById(consultationRequestDTO.getDoctorId())).thenReturn(Optional.of(userDoctor));

        ConsultationDTO result = consultationService.startConsultation(consultationRequestDTO);

        assertEquals(consultationRequestDTO.getConsultationDate(), result.getConsultationDate());
        assertEquals(user.getName(), result.getMember().getName());
        assertEquals(userDoctor.getName(), result.getDoctor().getName());

    }

    @Test
    void startConsultationDoctor_Success() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(userDoctor.getEmail());
        when(userRepository.findByEmail(userDoctor.getEmail())).thenReturn(Optional.of(userDoctor));
        when(userRepository.findById(consultationRequestDTO.getDoctorId())).thenReturn(Optional.of(userDoctor));
        when(consultationRepository.findById(any(Integer.class))).thenReturn(Optional.of(consultation));

        assertNotNull(userDoctor, "userDoctor should not be null");
        assertNotNull(consultation, "consultation should not be null");
        assertNotNull(consultationRequestDTO.getDoctorId(), "Doctor ID in consultationRequestDTO should not be null");

        ConsultationDTO result = consultationService.acceptConsultation(consultationAcceptRequestDTO, consultation.getId());

        assertTrue(result.isAccepted());
        assertEquals(consultationAcceptRequestDTO.getConsultationUrl(), result.getConsultationUrl());
        assertEquals(userDoctor.getName(), result.getDoctor().getName());
    }

    @Test
    void getAllConsultation_Success() {
        List<Consultation> consultationList = new ArrayList<>();
        consultationList.add(consultation);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(consultationRepository.findAll(any(Specification.class))).thenReturn(consultationList);

        List<ConsultationDTO> result = consultationService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(consultationRepository, times(1)).findAll(any(Specification.class));
    }

}