package br.com.fiap.Jannos.service;

import br.com.fiap.Jannos.dto.request.PessoaRequest;
import br.com.fiap.Jannos.dto.response.PessoaResponse;
import br.com.fiap.Jannos.entity.Pessoa;
import br.com.fiap.Jannos.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService implements ServiceDTO<Pessoa, PessoaRequest, PessoaResponse> {

    @Autowired
    private PessoaRepository repo;

    @Autowired
    private EnderecoService enderecoService;

    @Override
    public Pessoa toEntity(PessoaRequest r) {

        var endereco = enderecoService.findById( r.endereco().id() );

        return Pessoa.builder()
                .nome( r.nome() )
                .cpf( r.cpf() )
                .email( r.email() )
                .endereco( endereco )
                .build();

    }

    @Override
    public PessoaResponse toResponse(Pessoa e) {

        var endereco = enderecoService.toResponse( e.getEndereco() );

        return PessoaResponse.builder()
                .id( e.getId() )
                .nome( e.getNome() )
                .cpf( e.getCpf() )
                .email( e.getEmail() )
                .endereco( endereco )
                .build();

    }

    @Override
    public List<Pessoa> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Pessoa> findAll(Example<Pessoa> example) {
        return repo.findAll( example );
    }

    @Override
    public Pessoa findById(Long id) {
        return repo.findById( id ).orElse( null );
    }

    @Override
    public Pessoa save(Pessoa e) {
        return repo.save( e );
    }


}
