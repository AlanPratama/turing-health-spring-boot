package com.turinghealth.turing.health.utils.specification;

import com.turinghealth.turing.health.entity.enums.Role;
import com.turinghealth.turing.health.entity.meta.Consultation;
import com.turinghealth.turing.health.entity.meta.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ConsultationSpecification {

    public static Specification<Consultation> getSpecification(User user) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (user != null) {
                if (user.getRole() == Role.MEMBER) {
                    predicates.add(criteriaBuilder.equal(root.get("member"), user));
                } else {
                    predicates.add(criteriaBuilder.equal(root.get("doctor"), user));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }

}
