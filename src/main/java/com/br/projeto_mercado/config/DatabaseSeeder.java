package com.br.projeto_mercado.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.br.projeto_mercado.model.Item;
import com.br.projeto_mercado.model.ItemType;
import com.br.projeto_mercado.model.Personagem;
import com.br.projeto_mercado.model.Raridade;
import com.br.projeto_mercado.repository.ItemRepository;
import com.br.projeto_mercado.repository.PersonagemRepository;

import jakarta.annotation.PostConstruct;

@Configuration
public class DatabaseSeeder {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PersonagemRepository personagemRepository;

    @PostConstruct
    public void init() {

        var personagem1 = Personagem.builder()
                .nome("Guerreiro")
                .classe(com.br.projeto_mercado.model.PersonagemType.GUERREIRO)
                .nivel(10)
                .moedas(500)
                .build();

        var personagem2 = Personagem.builder()
                .nome("Mago")
                .classe(com.br.projeto_mercado.model.PersonagemType.MAGO)
                .nivel(15)
                .moedas(1000)
                .build();

        personagemRepository.saveAll(List.of(personagem1, personagem2));

        var itens = List.of(
                Item.builder()
                        .nome("Espada Lendária")
                        .tipo(ItemType.ARMA)
                        .raridade(Raridade.LENDARIO)
                        .preco(1500.0)
                        .dono(personagem1)
                        .build(),
                Item.builder()
                        .nome("Escudo Épico")
                        .tipo(ItemType.ARMADURA)
                        .raridade(Raridade.EPICO)
                        .preco(800.0)
                        .dono(personagem1)
                        .build(),
                Item.builder()
                        .nome("Poção de Vida")
                        .tipo(ItemType.POÇÃO)
                        .raridade(Raridade.COMUM)
                        .preco(50.0)
                        .dono(personagem2)
                        .build(),
                Item.builder()
                        .nome("Anel Mágico")
                        .tipo(ItemType.ACESSÓRIO)
                        .raridade(Raridade.RARO)
                        .preco(300.0)
                        .dono(personagem2)
                        .build());

        itemRepository.saveAll(itens);
    }
}