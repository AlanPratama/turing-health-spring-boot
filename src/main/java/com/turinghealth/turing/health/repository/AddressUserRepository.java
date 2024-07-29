package com.turinghealth.turing.health.repository;

import com.turinghealth.turing.health.entity.meta.transaction.AddressUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AddressUserRepository extends JpaRepository<AddressUser, Integer>, JpaSpecificationExecutor<AddressUser> {
}
