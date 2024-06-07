package br.com.fiap.Jannos.resource;

import br.com.fiap.Jannos.dto.request.RecursoRequest;
import br.com.fiap.Jannos.dto.response.RecursoResponse;
import br.com.fiap.Jannos.entity.Recurso;
import br.com.fiap.Jannos.repository.FocoRepository;
import br.com.fiap.Jannos.service.RecursoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/recurso")
public class RecursoResource implements ResourceDTO<RecursoRequest, RecursoResponse> {

    @Autowired
    private RecursoService service;

    @Autowired
    private FocoRepository focoRepository;

    @GetMapping
    public ResponseEntity<Collection<RecursoResponse>> findAll(
            @RequestParam(name = "valor", required = false) Double valor,
            @RequestParam(name = "data", required = false) LocalDate data,
            @RequestParam(name = "foco", required = false) Long idFoco
    ) {

        var foco = Objects.nonNull( idFoco ) ? focoRepository
                .findById( idFoco )
                .orElse( null ) : null;


        var recurso = Recurso.builder()
                .valor( valor )
                .data( data )
                .foco( foco )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Recurso> example = Example.of(recurso, matcher );

        var encontrados = service.findAll( example );
        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();
        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();
        return ResponseEntity.ok( resposta );
    }
    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<RecursoResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<RecursoResponse> save(@RequestBody @Valid RecursoRequest r) {
        var entity = service.toEntity( r );
        var saved = service.save( entity );
        var resposta = service.toResponse( saved );

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path( "/{id}" )
                .buildAndExpand( saved.getId() )
                .toUri();

        return ResponseEntity.created( uri ).body( resposta );
    }

}
