package br.com.fiap.Jannos.resource;

import br.com.fiap.Jannos.dto.request.EmpresaRequest;
import br.com.fiap.Jannos.dto.response.EmpresaResponse;
import br.com.fiap.Jannos.entity.Empresa;
import br.com.fiap.Jannos.repository.EnderecoRepository;
import br.com.fiap.Jannos.service.EmpresaService;
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
@RequestMapping(value = "/empresa")
public class EmpresaResource implements ResourceDTO<EmpresaRequest, EmpresaResponse> {

    @Autowired
    private EmpresaService service;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @GetMapping
    public ResponseEntity<Collection<EmpresaResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "area", required = false) String area,
            @RequestParam(name = "endereco", required = false) Long idEndereco
    ) {

        var endereco = Objects.nonNull( idEndereco ) ? enderecoRepository
                .findById( idEndereco )
                .orElse( null ) : null;

        var empresa = Empresa.builder()
                .nome( nome )
                .area( area )
                .endereco( endereco )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Empresa> example = Example.of(empresa, matcher );

        var encontrados = service.findAll( example );
        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();
        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();
        return ResponseEntity.ok( resposta );
    }
    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<EmpresaResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<EmpresaResponse> save(@RequestBody @Valid EmpresaRequest r) {
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
