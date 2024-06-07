package br.com.fiap.Jannos.service;

import br.com.fiap.Jannos.dto.request.EnderecoRequest;
import br.com.fiap.Jannos.dto.response.EnderecoResponse;
import br.com.fiap.Jannos.entity.Endereco;
import br.com.fiap.Jannos.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService implements ServiceDTO<Endereco, EnderecoRequest, EnderecoResponse> {

    @Autowired
    private EnderecoRepository repo;

    @Override
    public Endereco toEntity(EnderecoRequest r) {

        return Endereco.builder()
                .latitude( r.latitude() )
                .longitude( r.longitude() )
                .cep( r.cep() )
                .numero( r.numero() )
                .complemento( r.complemento() )
                .build();

    }

    @Override
    public EnderecoResponse toResponse(Endereco e) {

        return EnderecoResponse.builder()
                .id( e.getId() )
                .latitude( e.getLatitude() )
                .longitude( e.getLongitude() )
                .cep( e.getCep() )
                .numero( e.getNumero() )
                .complemento( e.getComplemento() )
                .build();

    }

    @Override
    public List<Endereco> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Endereco> findAll(Example<Endereco> example) {
        return repo.findAll( example );
    }

    @Override
    public Endereco findById(Long id) {
        return repo.findById( id ).orElse( null );
    }

    @Override
    public Endereco save(Endereco e) {
        return repo.save( e );
    }

}
