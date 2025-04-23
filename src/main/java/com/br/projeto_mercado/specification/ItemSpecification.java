package com.br.projeto_mercado.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.br.projeto_mercado.model.Item;
import com.br.projeto_mercado.model.ItemFilter;

import jakarta.persistence.criteria.Predicate;

public class ItemSpecification {

    public static Specification<Item> withFilters(ItemFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.nome() != null && !filter.nome().isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("nome")), "%" + filter.nome().toLowerCase() + "%"));
            }

            if (filter.tipo() != null) {
                predicates.add(cb.equal(root.get("tipo"), filter.tipo()));
            }

            if (filter.raridade() != null) {
                predicates.add(cb.equal(root.get("raridade"), filter.raridade()));
            }

            if (filter.precoMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("preco"), filter.precoMin()));
            }

            if (filter.precoMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("preco"), filter.precoMax()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}