package com.sinoiift.repository;

import com.sinoiift.domain.Pkg;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by xg on 2018/1/15.
 */
public class PkgSpecifications {
    public static Specification<Pkg> pkgLikeSpecification(final String key, final String value) {
        return new Specification<Pkg>() {
            public Predicate toPredicate(Root<Pkg> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.<String>get(key), "%" + value + "%");
            }
        };
    }
}
