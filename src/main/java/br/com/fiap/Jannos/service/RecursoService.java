package br.com.fiap.Jannos.service;

import br.com.fiap.Jannos.dto.request.RecursoRequest;
import br.com.fiap.Jannos.dto.response.RecursoResponse;
import br.com.fiap.Jannos.entity.Recurso;
import br.com.fiap.Jannos.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecursoService implements ServiceDTO<Recurso, RecursoRequest, RecursoResponse> {

    @Autowired
    private RecursoRepository repo;

    @Autowired
    private FocoService focoService;

    @Override
    public Recurso toEntity(RecursoRequest r) {

        var foco = focoService.findById( r.foco().id() );

        return Recurso.builder()
                .valor( r.valor() )
                .data( r.data() )
                .foco( foco )
                .build();

    }

    @Override
    public RecursoResponse toResponse(Recurso e) {

        var foco = focoService.toResponse( e.getFoco() );

        return RecursoResponse.builder()
                .id( e.getId() )
                .valor( e.getValor() )
                .data( e.getData() )
                .foco( foco )
                .build();

    }

    @Override
    public List<Recurso> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Recurso> findAll(Example<Recurso> example) {
        return repo.findAll( example );
    }

    @Override
    public Recurso findById(Long id) {
        return repo.findById( id ).orElse( null );
    }

    @Override
    public Recurso save(Recurso e) {
        return repo.save( e );
    }

}
