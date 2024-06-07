package br.com.fiap.Jannos.resource;

import br.com.fiap.Jannos.dto.request.DenunciaRequest;
import br.com.fiap.Jannos.dto.response.DenunciaResponse;
import br.com.fiap.Jannos.entity.Denuncia;
import br.com.fiap.Jannos.repository.PessoaRepository;
import br.com.fiap.Jannos.service.DenunciaService;
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
@RequestMapping(value = "/denuncia")
public class DenunciaResource implements ResourceDTO<DenunciaRequest, DenunciaResponse> {

    @Autowired
    private DenunciaService service;

    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping
    public ResponseEntity<Collection<DenunciaResponse>> findAll(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "descricao", required = false) String descricao,
            @RequestParam(name = "pessoa", required = false) Long idPessoa
    ) {

        var pessoa = Objects.nonNull( idPessoa ) ? pessoaRepository
                .findById( idPessoa )
                .orElse( null ) : null;

        var denuncia = Denuncia.builder()
                .status( status )
                .descricao( descricao )
                .pessoa( pessoa )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Denuncia> example = Example.of(denuncia, matcher );

        var encontrados = service.findAll( example );
        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();
        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();
        return ResponseEntity.ok( resposta );
    }
    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<DenunciaResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<DenunciaResponse> save(@RequestBody @Valid DenunciaRequest r) {
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
