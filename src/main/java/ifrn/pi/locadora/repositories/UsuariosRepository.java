package ifrn.pi.locadora.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import ifrn.pi.locadora.models.Usuarios;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long>{
	
	Usuarios findByEmail(String email);

	
	
}
