package com.turinghealth.turing.health.service.impl;

import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.entity.meta.transaction.AddressUser;
import com.turinghealth.turing.health.repository.AddressUserRepository;
import com.turinghealth.turing.health.repository.UserRepository;
import com.turinghealth.turing.health.service.AddressUserService;
import com.turinghealth.turing.health.utils.adviser.exception.AuthenticationException;
import com.turinghealth.turing.health.utils.adviser.exception.NotFoundException;
import com.turinghealth.turing.health.utils.dto.addressUserDTO.AddressUserRequestDTO;
import com.turinghealth.turing.health.utils.specification.AddressUserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;

@Service
@RequiredArgsConstructor
public class AddressUserServiceImpl implements AddressUserService {
    private final AddressUserRepository addressUserRepository;
    private final UserRepository userRepository;

    @Override
    public AddressUser create(AddressUserRequestDTO request) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

        AddressUser addressUser = AddressUser.builder()
                .user(user)
                .buyerName(request.getBuyerName())
                .buyerPhone(request.getBuyerPhone())
                .city(request.getCity())
                .region(request.getRegion())
                .posCode(request.getPosCode())
                .addressDetail(request.getAddressDetail())
                .fixPoint(request.getFixPoint())
                .type(request.getType())
                .message(request.getMessage())
                .build();

        return addressUserRepository.save(addressUser);
    }

    @Override
    public List<AddressUser> getAll() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));


        Specification<AddressUser> spec = AddressUserSpecification.getSpecification(user);
        List<AddressUser> addressUserList = addressUserRepository.findAll(spec);

        return addressUserList;
    }

    @Override
    public AddressUser getOne(Integer id) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationException("Please Login / Register Again!"));

        AddressUser addressUser = addressUserRepository.findById(id).orElseThrow(() -> new NotFoundException("Address Is Not Found!"));

        if (!Objects.equals(addressUser.getUser().getId(), user.getId())) {
            throw new NotFoundException("Address Is Not Yours!");
        }

        return addressUser;
    }

    @Override
    public AddressUser update(AddressUserRequestDTO request, Integer id) {
        AddressUser addressUser = this.getOne(id);
        addressUser.setBuyerName(request.getBuyerName());
        addressUser.setBuyerPhone(request.getBuyerPhone());
        addressUser.setCity(request.getCity());
        addressUser.setRegion(request.getRegion());
        addressUser.setPosCode(request.getPosCode());
        addressUser.setAddressDetail(request.getAddressDetail());
        addressUser.setFixPoint(request.getFixPoint());
        addressUser.setType(request.getType());
        addressUser.setMessage(request.getMessage());

        return addressUserRepository.save(addressUser);
    }

    @Override
    public void delete(Integer id) {
        AddressUser addressUser = this.getOne(id);

        addressUserRepository.delete(addressUser);
    }
}
