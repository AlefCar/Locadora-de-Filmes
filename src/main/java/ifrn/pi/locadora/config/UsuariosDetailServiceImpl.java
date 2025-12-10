package ifrn.pi.locadora.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ifrn.pi.locadora.models.Usuarios;
import ifrn.pi.locadora.repositories.UsuariosRepository;

@Service
public class UsuariosDetailServiceImpl implements UserDetailsService{

	@Autowired
	private UsuariosRepository ur;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuarios usuario = ur.findByEmail(username);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario não encontrado");
			
			
		}

		
		return usuario;
	}

}
