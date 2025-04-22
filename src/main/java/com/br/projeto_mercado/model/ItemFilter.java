package com.br.projeto_mercado.model;

public record ItemFilter(
        String nome,
        ItemType tipo,
        Raridade raridade,
        Double precoMin,
        Double precoMax) {
}