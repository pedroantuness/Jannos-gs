package br.com.fiap.Jannos.service;

import br.com.fiap.Jannos.dto.request.EmpresaRequest;
import br.com.fiap.Jannos.dto.response.EmpresaResponse;
import br.com.fiap.Jannos.entity.Empresa;
import br.com.fiap.Jannos.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService implements ServiceDTO<Empresa, EmpresaRequest, EmpresaResponse> {

    @Autowired
    private EmpresaRepository repo;

    @Autowired
    private EnderecoService enderecoService;

    @Override
    public Empresa toEntity(EmpresaRequest r) {

        var endereco = enderecoService.findById( r.endereco().id() );

        return Empresa.builder()
                .nome( r.nome() )
                .area( r.area() )
                .endereco( endereco )
                .build();

    }

    @Override
    public EmpresaResponse toResponse(Empresa e) {

        var endereco = enderecoService.toResponse( e.getEndereco() );

        return EmpresaResponse.builder()
                .id( e.getId() )
                .nome( e.getNome() )
                .area( e.getArea() )
                .endereco( endereco )
                .build();

    }

    @Override
    public List<Empresa> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Empresa> findAll(Example<Empresa> example) {
        return repo.findAll( example );
    }

    @Override
    public Empresa findById(Long id) {
        return repo.findById( id ).orElse( null );
    }

    @Override
    public Empresa save(Empresa e) {
        return repo.save( e );
    }

}
