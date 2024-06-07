package br.com.fiap.Jannos.service;

import br.com.fiap.Jannos.dto.request.FocoRequest;
import br.com.fiap.Jannos.dto.response.FocoResponse;
import br.com.fiap.Jannos.entity.Endereco;
import br.com.fiap.Jannos.entity.Foco;
import br.com.fiap.Jannos.repository.FocoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FocoService implements ServiceDTO<Foco, FocoRequest, FocoResponse> {

    @Autowired
    private FocoRepository repo;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private DenunciaService denunciaService;

    @Override
    public Foco toEntity(FocoRequest r) {

        var endereco = enderecoService.findById( r.endereco().id() );
        var denuncia = denunciaService.findById( r.denuncia().id() );

        return Foco.builder()
                .descricao( r.descricao() )
                .endereco( endereco )
                .denuncia( denuncia )
                .build();

    }

    @Override
    public FocoResponse toResponse(Foco e) {

        var endereco = enderecoService.toResponse( e.getEndereco() );
        var denuncia = denunciaService.toResponse( e.getDenuncia() );

        return FocoResponse.builder()
                .id( e.getId() )
                .descricao( e.getDescricao() )
                .endereco( endereco )
                .denuncia( denuncia )
                .build();

    }

    @Override
    public List<Foco> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Foco> findAll(Example<Foco> example) {
        return repo.findAll( example );
    }

    @Override
    public Foco findById(Long id) {
        return repo.findById( id ).orElse( null );
    }

    @Override
    public Foco save(Foco e) {
        return repo.save( e );
    }

}
