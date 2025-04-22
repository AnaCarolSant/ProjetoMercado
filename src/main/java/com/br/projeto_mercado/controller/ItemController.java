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

import com.br.projeto_mercado.model.Item;
import com.br.projeto_mercado.repository.ItemRepository;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/itens")
@Slf4j
public class ItemController {

    @Autowired
    private ItemRepository repository;

    @GetMapping
    @Cacheable(value = "itens")
    @Operation(summary = "Listar todos os itens", description = "Lista todos os itens disponíveis no mercado", tags = "Item")
    public List<Item> index() {
        log.info("Listando todos os itens");
        return repository.findAll();
    }

    @PostMapping
    @CacheEvict(value = "itens", allEntries = true)
    @Operation(summary = "Criar um novo item", description = "Adiciona um novo item ao mercado", tags = "Item")
    @ResponseStatus(HttpStatus.CREATED)
    public Item create(@RequestBody @Valid Item item) {
        log.info("Cadastrando item: " + item.getNome());
        return repository.save(item);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar um item por ID", description = "Busca um item específico pelo ID", tags = "Item")
    public Item get(@PathVariable Long id) {
        log.info("Buscando item com ID: " + id);
        return getItem(id);
    }

    @PutMapping("{id}")
    @CacheEvict(value = "itens", allEntries = true)
    @Operation(summary = "Atualizar um item por ID", description = "Atualiza os dados de um item existente", tags = "Item")
    public Item update(@PathVariable Long id, @RequestBody @Valid Item itemAtualizado) {
        log.info("Atualizando item com ID: " + id + " - " + itemAtualizado);
        Item item = getItem(id);
        item.setNome(itemAtualizado.getNome());
        item.setTipo(itemAtualizado.getTipo());
        item.setRaridade(itemAtualizado.getRaridade());
        item.setPreco(itemAtualizado.getPreco());
        item.setDono(itemAtualizado.getDono());
        return repository.save(item);
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "itens", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar um item por ID", description = "Remove um item do mercado", tags = "Item")
    public void destroy(@PathVariable Long id) {
        log.info("Apagando item com ID: " + id);
        repository.delete(getItem(id));
    }

    private Item getItem(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Item com ID " + id + " não encontrado"));
    }
}