package br.com.fiap.Jannos.resource;

import br.com.fiap.Jannos.dto.request.FocoRequest;
import br.com.fiap.Jannos.dto.response.FocoResponse;
import br.com.fiap.Jannos.entity.Foco;
import br.com.fiap.Jannos.repository.DenunciaRepository;
import br.com.fiap.Jannos.repository.EnderecoRepository;
import br.com.fiap.Jannos.service.FocoService;
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
@RequestMapping(value = "/foco")
public class FocoResource implements ResourceDTO<FocoRequest, FocoResponse> {

    @Autowired
    private FocoService service;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private DenunciaRepository denunciaRepository;

    @GetMapping
    public ResponseEntity<Collection<FocoResponse>> findAll(
            @RequestParam(name = "descricao", required = false) String descricao,
            @RequestParam(name = "endereco", required = false) Long idEndereco,
            @RequestParam(name = "denuncia", required = false) Long idDenuncia
    ) {

        var endereco = Objects.nonNull( idEndereco ) ? enderecoRepository
                .findById( idEndereco )
                .orElse( null ) : null;

        var denuncia = Objects.nonNull( idDenuncia ) ? denunciaRepository
                .findById( idDenuncia )
                .orElse( null ) : null;

        var foco = Foco.builder()
                .descricao( descricao )
                .endereco( endereco )
                .denuncia( denuncia )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Foco> example = Example.of( foco, matcher );

        var encontrados = service.findAll( example );
        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();
        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();
        return ResponseEntity.ok( resposta );
    }
    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<FocoResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<FocoResponse> save(@RequestBody @Valid FocoRequest r) {
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
