package br.com.fiap.Jannos.resource;

import br.com.fiap.Jannos.dto.request.DroneRequest;
import br.com.fiap.Jannos.dto.response.DroneResponse;
import br.com.fiap.Jannos.entity.Drone;
import br.com.fiap.Jannos.service.DroneService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping(value = "/drone")
public class DroneResource implements ResourceDTO<DroneRequest, DroneResponse> {

    @Autowired
    private DroneService service;

    @GetMapping
    public ResponseEntity<Collection<DroneResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "tipo", required = false) String tipo,
            @RequestParam(name = "modelo", required = false) String modelo
    ) {

        var drone = Drone.builder()
                .nome( nome )
                .tipo( tipo )
                .modelo( modelo )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Drone> example = Example.of( drone, matcher );

        var encontrados = service.findAll( example );
        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();
        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();
        return ResponseEntity.ok( resposta );
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<DroneResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<DroneResponse> save(@RequestBody @Valid DroneRequest r) {
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
