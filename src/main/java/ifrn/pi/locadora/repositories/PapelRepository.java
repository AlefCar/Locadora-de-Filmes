// Define o pacote onde este repositório está localizado
package ifrn.pi.locadora.repositories;

// Importa a interface JpaRepository do Spring Data JPA
import org.springframework.data.jpa.repository.JpaRepository;

// Importa a entidade Papel (Role / Permissão)
import ifrn.pi.locadora.models.Papel;

// Interface responsável pelo acesso ao banco de dados da entidade Papel
public interface PapelRepository extends JpaRepository<Papel, Long> {
	
	// =========================
	// BUSCAR PAPEL PELO NOME
	// =========================
	
	// Método que busca um papel (role) pelo nome
	// Exemplo: "ROLE_ADMIN", "ROLE_ATENDENTE", "ROLE_USUARIOS"
	// O Spring Data JPA cria automaticamente a query
	Papel findByNome(String nome);

}
