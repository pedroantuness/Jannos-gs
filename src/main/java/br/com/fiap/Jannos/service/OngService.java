package br.com.fiap.Jannos.service;

import br.com.fiap.Jannos.dto.request.OngRequest;
import br.com.fiap.Jannos.dto.response.OngResponse;
import br.com.fiap.Jannos.entity.Ong;
import br.com.fiap.Jannos.repository.OngRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OngService implements ServiceDTO<Ong, OngRequest, OngResponse> {

    @Autowired
    private OngRepository repo;

    @Autowired
    private DroneService droneService;

    @Autowired
    private EnderecoService enderecoService;

    @Override
    public Ong toEntity(OngRequest r) {

        var endereco = enderecoService.findById( r.endereco().id() );

        return Ong.builder()
                .nome( r.nome() )
                .area( r.area() )
                .endereco( endereco )
                .build();
    }

    @Override
    public OngResponse toResponse(Ong e) {

        var endereco = enderecoService.toResponse( e.getEndereco() );
        var drones = e.getDrones().stream().map(droneService::toResponse).toList();

        return OngResponse.builder()
                .id( e.getId() )
                .nome( e.getNome() )
                .area( e.getArea() )
                .endereco( endereco )
                .drones( drones )
                .build();
    }

    @Override
    public List<Ong> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Ong> findAll(Example<Ong> example) {
        return repo.findAll( example );
    }

    @Override
    public Ong findById(Long id) {
        return repo.findById( id ).orElse( null );
    }

    @Override
    public Ong save(Ong e) {
        return repo.save( e );
    }


}
