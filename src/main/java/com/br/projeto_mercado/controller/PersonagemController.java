package com.br.projeto_mercado.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.br.projeto_mercado.model.Personagem;
import com.br.projeto_mercado.model.PersonagemFilter;
import com.br.projeto_mercado.repository.PersonagemRepository;
import com.br.projeto_mercado.specification.PersonagemSpecification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/personagens")
@Slf4j
public class PersonagemController {

    @Autowired
    private PersonagemRepository repository;

    @GetMapping
    @Cacheable(value = "personagens")
    @Operation(summary = "Listar todos os personagens com filtros", description = "Lista todos os personagens aplicando filtros opcionais", tags = "Personagem")
    public List<Personagem> index(PersonagemFilter filter) {
        log.info("Listando personagens com filtros: " + filter);
        return repository.findAll(PersonagemSpecification.withFilters(filter));
    }

    @PostMapping
    @CacheEvict(value = "personagens", allEntries = true)
    @Operation(summary = "Criar um novo personagem", responses = @ApiResponse(responseCode = "400"))
    @ResponseStatus(HttpStatus.CREATED)
    public Personagem create(@RequestBody @Valid Personagem personagem) {
        log.info("Cadastrando personagem: " + personagem.getNome());
        return repository.save(personagem);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar um personagem por ID")
    public Personagem get(@PathVariable Long id) {
        log.info("Buscando personagem com ID: " + id);
        return getPersonagem(id);
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "personagens", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar um personagem por ID")
    public void destroy(@PathVariable Long id) {
        log.info("Apagando personagem com ID: " + id);
        repository.delete(getPersonagem(id));
    }

    @PutMapping("{id}")
    @CacheEvict(value = "personagens", allEntries = true)
    @Operation(summary = "Atualizar um personagem por ID")
    public Personagem update(@PathVariable Long id, @RequestBody @Valid Personagem personagemAtualizado) {
        log.info("Atualizando personagem com ID: " + id + " - " + personagemAtualizado);
        Personagem personagem = getPersonagem(id);
        personagem.setNome(personagemAtualizado.getNome());
        personagem.setClasse(personagemAtualizado.getClasse());
        personagem.setNivel(personagemAtualizado.getNivel());
        personagem.setMoedas(personagemAtualizado.getMoedas());
        return repository.save(personagem);
    }

    private Personagem getPersonagem(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Personagem com ID " + id + " não encontrado"));
    }
}