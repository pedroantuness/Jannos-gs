package br.com.fiap.Jannos.service;

import br.com.fiap.Jannos.dto.request.DroneRequest;
import br.com.fiap.Jannos.dto.response.DroneResponse;
import br.com.fiap.Jannos.entity.Drone;
import br.com.fiap.Jannos.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneService implements ServiceDTO<Drone, DroneRequest, DroneResponse> {

    @Autowired
    private DroneRepository repo;

    @Override
    public Drone toEntity(DroneRequest r) {

        return Drone.builder()
                .nome( r.nome() )
                .tipo( r.tipo() )
                .modelo( r.modelo() )
                .build();

    }

    @Override
    public DroneResponse toResponse(Drone e) {

        return DroneResponse.builder()
                .id( e.getId() )
                .nome( e.getNome() )
                .tipo( e.getTipo() )
                .modelo( e.getModelo() )
                .build();

    }

    @Override
    public List<Drone> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Drone> findAll(Example<Drone> example) {
        return repo.findAll( example );
    }

    @Override
    public Drone findById(Long id) {
        return repo.findById( id ).orElse( null );
    }

    @Override
    public Drone save(Drone e) {
        return repo.save( e );
    }

}
