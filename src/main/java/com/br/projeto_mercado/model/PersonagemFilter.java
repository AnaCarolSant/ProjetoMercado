package com.br.projeto_mercado.model;

public record PersonagemFilter(
        String nome,
        PersonagemType classe,
        Integer nivelMin,
        Integer nivelMax) {
}