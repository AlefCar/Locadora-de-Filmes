package ifrn.pi.locadora.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.pi.locadora.models.Filme;

public interface FilmeRepository extends JpaRepository<Filme, Long> {

}
