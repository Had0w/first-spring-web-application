package com.klyuev.demoboot.repositories.specifications;

import com.klyuev.demoboot.entities.Item;
import org.springframework.data.jpa.domain.Specification;

public class ItemSpecification {
    public static Specification<Item> titleContains(String word) {
        return (Specification<Item>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), "%" + word + "%");
    }

    public static Specification<Item> greaterOrEquals(Integer cost) {
        return (Specification<Item>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), cost);
    }

    public static Specification<Item> lesserOrEquals(Integer cost) {
        return (Specification<Item>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("cost"), cost);
    }
}
