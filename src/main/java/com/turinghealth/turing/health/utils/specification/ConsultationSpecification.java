package com.turinghealth.turing.health.utils.specification;

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
                predicates.add(criteriaBuilder.equal(root.get("user"), user));
            }

            return criteriaBuilder.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }

}
