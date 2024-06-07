package br.com.fiap.Jannos.resource;

import br.com.fiap.Jannos.dto.request.EnderecoRequest;
import br.com.fiap.Jannos.dto.response.EnderecoResponse;
import br.com.fiap.Jannos.entity.Endereco;
import br.com.fiap.Jannos.service.EnderecoService;
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
@RequestMapping(value = "/endereco")
public class EnderecoResource implements ResourceDTO<EnderecoRequest, EnderecoResponse> {

    @Autowired
    private EnderecoService service;

    @GetMapping
    public ResponseEntity<Collection<EnderecoResponse>> findAll(
            @RequestParam(name = "latitude", required = false) String latitude,
            @RequestParam(name = "longitude", required = false) String longitude,
            @RequestParam(name = "cep", required = false) String cep,
            @RequestParam(name = "numero", required = false) Integer numero,
            @RequestParam(name = "complemento", required = false) String complemento
    ) {

        var endereco = Endereco.builder()
                .latitude( latitude )
                .longitude( longitude )
                .cep( cep )
                .numero( numero )
                .complemento( complemento )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Endereco> example = Example.of( endereco, matcher );

        var encontrados = service.findAll( example );
        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();
        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();
        return ResponseEntity.ok( resposta );
    }
    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<EnderecoResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<EnderecoResponse> save(@RequestBody @Valid EnderecoRequest r) {
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
