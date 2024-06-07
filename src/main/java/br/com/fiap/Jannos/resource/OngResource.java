package br.com.fiap.Jannos.resource;

import br.com.fiap.Jannos.dto.request.DroneRequest;
import br.com.fiap.Jannos.dto.request.OngRequest;
import br.com.fiap.Jannos.dto.response.DroneResponse;
import br.com.fiap.Jannos.dto.response.OngResponse;
import br.com.fiap.Jannos.entity.Drone;
import br.com.fiap.Jannos.entity.Ong;
import br.com.fiap.Jannos.repository.EnderecoRepository;
import br.com.fiap.Jannos.service.DroneService;
import br.com.fiap.Jannos.service.OngService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/ong")
public class OngResource implements ResourceDTO<OngRequest, OngResponse> {

    @Autowired
    private OngService service;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private DroneService droneService;

    @GetMapping
    public ResponseEntity<Collection<OngResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "area", required = false) String area,
            @RequestParam(name = "endereco", required = false) Long idEndereco
    ) {

        var endereco = Objects.nonNull( idEndereco ) ? enderecoRepository
                .findById( idEndereco )
                .orElse( null ) : null;

        var ong = Ong.builder()
                .nome( nome )
                .area( area )
                .endereco( endereco )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Ong> example = Example.of( ong, matcher );

        var encontrados = service.findAll( example );
        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();
        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();
        return ResponseEntity.ok( resposta );
    }


    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<OngResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<OngResponse> save(@RequestBody @Valid OngRequest r) {
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

    @Transactional
    @PostMapping("/{id}/drones")
    public ResponseEntity<OngResponse> saveDrone(@RequestBody @Valid DroneRequest r, @PathVariable Long id) {
        var ong = service.findById( id );
        if(ong == null) return ResponseEntity.notFound().build();

        Drone drone = droneService.toEntity( r );
        var drones = ong.getDrones();
        drones.add(drone);
        ong.setDrones(drones);

        var saved = service.save( ong );
        var resposta = service.toResponse( saved );

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path( "/{id}" )
                .buildAndExpand( saved.getId() )
                .toUri();

        return ResponseEntity.created( uri ).body( resposta );
    }

    @Transactional
    @GetMapping("/{id}/drones")
    public ResponseEntity<Collection<DroneResponse>> findDrones(@PathVariable Long id) {
        var ong = service.findById( id );
        if(ong == null) return ResponseEntity.notFound().build();

        var drones = ong.getDrones();
        if(drones.isEmpty()) return ResponseEntity.noContent().build();

        var resposta = drones.stream()
                .map( droneService::toResponse )
                .toList();

        return ResponseEntity.ok(resposta);
    }

}
