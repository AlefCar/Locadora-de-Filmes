// Define o pacote onde está a implementação do serviço de usuários
package ifrn.pi.locadora.config;

// Importações do Spring Security
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Importações da aplicação
import ifrn.pi.locadora.models.Usuarios;
import ifrn.pi.locadora.repositories.UsuariosRepository;

// Indica que esta classe é um Service gerenciado pelo Spring
@Service
public class UsuariosDetailServiceImpl implements UserDetailsService {

	// Injeta o repositório de usuários
	@Autowired
	private UsuariosRepository ur;
	
	// =========================
	// AUTENTICAÇÃO DO USUÁRIO
	// =========================
	
	// Método obrigatório da interface UserDetailsService
	// Ele é chamado automaticamente pelo Spring Security no login
	@Override
	public UserDetails loadUserByUsername(String username) 
	        throws UsernameNotFoundException {
		
		// Busca o usuário no banco usando o email (login)
		Usuarios usuario = ur.findByEmail(username);
		
		// Se não encontrar o usuário, lança exceção
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario não encontrado");
		}

		// Retorna o usuário
		// A classe Usuarios deve implementar UserDetails
		return usuario;
	}
}
