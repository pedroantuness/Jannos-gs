package br.com.fiap.Jannos.repository;

import br.com.fiap.Jannos.entity.Foco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FocoRepository extends JpaRepository<Foco, Long> {
}
