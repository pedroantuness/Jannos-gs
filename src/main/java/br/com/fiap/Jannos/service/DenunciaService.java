package br.com.fiap.Jannos.service;

import br.com.fiap.Jannos.dto.request.DenunciaRequest;
import br.com.fiap.Jannos.dto.response.DenunciaResponse;
import br.com.fiap.Jannos.entity.Denuncia;
import br.com.fiap.Jannos.repository.DenunciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DenunciaService implements ServiceDTO<Denuncia, DenunciaRequest, DenunciaResponse> {

    @Autowired
    private DenunciaRepository repo;

    @Autowired
    private PessoaService pessoaService;

    @Override
    public Denuncia toEntity(DenunciaRequest r) {

        var pessoa = pessoaService.findById( r.pessoa().id() );

        return Denuncia.builder()
                .status( r.status() )
                .descricao( r.descricao() )
                .pessoa( pessoa )
                .build();

    }

    @Override
    public DenunciaResponse toResponse(Denuncia e) {

        var pessoa = pessoaService.toResponse( e.getPessoa() );

        return DenunciaResponse.builder()
                .id( e.getId() )
                .status( e.getStatus() )
                .descricao( e.getDescricao() )
                .pessoa( pessoa )
                .build();

    }

    @Override
    public List<Denuncia> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Denuncia> findAll(Example<Denuncia> example) {
        return repo.findAll( example );
    }

    @Override
    public Denuncia findById(Long id) {
        return repo.findById( id ).orElse( null );
    }

    @Override
    public Denuncia save(Denuncia e) {
        return repo.save( e );
    }

}
