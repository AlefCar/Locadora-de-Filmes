package ifrn.pi.locadora.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.pi.locadora.models.Papel;

public interface PapelRepository  extends JpaRepository<Papel, Long>{
	
	Papel findByNome(String nome);

}
