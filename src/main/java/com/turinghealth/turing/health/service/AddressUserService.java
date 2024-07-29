package com.turinghealth.turing.health.service;

import com.turinghealth.turing.health.entity.meta.transaction.AddressUser;
import com.turinghealth.turing.health.utils.dto.addressUserDTO.AddressUserRequestDTO;

import java.util.List;

public interface AddressUserService {

    AddressUser create(AddressUserRequestDTO request);
    List<AddressUser> getAll();
    AddressUser getOne(Integer id);
    AddressUser update(AddressUserRequestDTO request, Integer id);
    void delete(Integer id);


}
