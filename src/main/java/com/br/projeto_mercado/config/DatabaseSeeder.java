package com.br.projeto_mercado.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.br.projeto_mercado.model.Personagem;
import com.br.projeto_mercado.model.PersonagemType;
import com.br.projeto_mercado.repository.PersonagemRepository;

import jakarta.annotation.PostConstruct;

@Configuration
public class DatabaseSeeder {

    @Autowired
    private PersonagemRepository personagemRepository;

    @PostConstruct
    public void init() {
        var nomes = List.of("Arqueiro", "Mago", "Guerreiro", "Paladino", "Assassino", "Bárbaro", "Clérigo", "Druida");
        var classes = PersonagemType.values();

        var personagens = new ArrayList<Personagem>();
        for (int i = 0; i < 20; i++) {
            personagens.add(
                    Personagem.builder()
                            .nome(nomes.get(new Random().nextInt(nomes.size())))
                            .classe(classes[new Random().nextInt(classes.length)])
                            .nivel(1 + new Random().nextInt(99))
                            .moedas(new Random().nextInt(1000))
                            .build());
        }

        personagemRepository.saveAll(personagens);
    }
}