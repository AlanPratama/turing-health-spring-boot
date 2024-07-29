package com.turinghealth.turing.health.utils.specification;

import com.turinghealth.turing.health.entity.meta.User;
import com.turinghealth.turing.health.entity.meta.transaction.AddressUser;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AddressUserSpecification {
    public static Specification<AddressUser> getSpecification(User user) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (user != null) {
                predicates.add(criteriaBuilder.equal(root.get("user"), user));
            }

            return criteriaBuilder.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };

    }
}
