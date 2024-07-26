package com.turinghealth.turing.health.utils.specification;

import com.turinghealth.turing.health.entity.meta.Hospital;
import com.turinghealth.turing.health.entity.meta.Region;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HospitalSpecification {

    public static Specification<Hospital> getSpecification(
            String name,
            String province,
            Region region
            ){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()){
                predicates.add(criteriaBuilder.like(root.get("name"), "%"+name+"%"));
            }
            if (province != null && !province.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("province"), "%"+province+"%"));
            }
            if (region != null){
                predicates.add(criteriaBuilder.equal(root.get("region"), region));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
