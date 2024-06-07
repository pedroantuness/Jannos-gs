package br.com.fiap.Jannos.resource;

import br.com.fiap.Jannos.dto.request.PessoaRequest;
import br.com.fiap.Jannos.dto.response.PessoaResponse;
import br.com.fiap.Jannos.entity.Pessoa;
import br.com.fiap.Jannos.repository.EnderecoRepository;
import br.com.fiap.Jannos.service.PessoaService;
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
@RequestMapping(value = "/pessoa")
public class PessoaResource implements ResourceDTO<PessoaRequest, PessoaResponse> {

    @Autowired
    private PessoaService service;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @GetMapping
    public ResponseEntity<Collection<PessoaResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "cpf", required = false) String cpf,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "endereco", required = false) Long idEndereco
    ) {

        var endereco = Objects.nonNull( idEndereco ) ? enderecoRepository
                .findById( idEndereco )
                .orElse( null ) : null;

        var pessoa = Pessoa.builder()
                .nome( nome )
                .cpf( cpf )
                .email( email )
                .endereco( endereco )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Pessoa> example = Example.of( pessoa, matcher );

        var encontrados = service.findAll( example );
        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();
        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();
        return ResponseEntity.ok( resposta );
    }
    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<PessoaResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<PessoaResponse> save(@RequestBody @Valid PessoaRequest r) {
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
