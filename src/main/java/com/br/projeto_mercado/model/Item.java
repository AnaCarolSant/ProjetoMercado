package com.br.projeto_mercado.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "campo obrigatório")
    private String nome;

    @Nonnull
    @Enumerated(EnumType.STRING)
    private ItemType tipo;

    @Nonnull
    @Enumerated(EnumType.STRING)
    private Raridade raridade;

    @Positive(message = "O preço deve ser maior que zero")
    private double preco;

    @ManyToOne
    @JoinColumn(name = "personagem_id")
    private Personagem dono;
}