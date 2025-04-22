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
            // Lista de predicados
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por nome (contém)
            if (filter.nome() != null && !filter.nome().isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("nome")), "%" + filter.nome().toLowerCase() + "%"));
            }

            // Filtro por classe
            if (filter.classe() != null) {
                predicates.add(cb.equal(root.get("classe"), filter.classe()));
            }

            // Filtro por nível mínimo
            if (filter.nivelMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("nivel"), filter.nivelMin()));
            }

            // Filtro por nível máximo
            if (filter.nivelMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("nivel"), filter.nivelMax()));
            }

            // Combina todos os predicados com AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}