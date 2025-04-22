package com.br.projeto_mercado.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.br.projeto_mercado.model.Personagem;
import com.br.projeto_mercado.model.PersonagemFilter;

import jakarta.persistence.criteria.Predicate;

public class PersonagemSpecification {

    public static Specification<Personagem> withFilters(PersonagemFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.nome() != null && !filter.nome().isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("nome")), "%" + filter.nome().toLowerCase() + "%"));
            }

            if (filter.classe() != null) {
                predicates.add(cb.equal(root.get("classe"), filter.classe()));
            }

            if (filter.nivelMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("nivel"), filter.nivelMin()));
            }

            if (filter.nivelMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("nivel"), filter.nivelMax()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}