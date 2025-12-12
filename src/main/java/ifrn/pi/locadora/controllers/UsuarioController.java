package ifrn.pi.locadora.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ifrn.pi.locadora.models.Papel;
import ifrn.pi.locadora.models.Usuarios;
import ifrn.pi.locadora.repositories.PapelRepository;
import ifrn.pi.locadora.repositories.UsuariosRepository;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private PapelRepository pr;
	@Autowired
	private UsuariosRepository ur;
	
	@GetMapping("/form")
	public String form() {
		return "usuarios/form";
	}
	
	@PostMapping
	public String salvar(Usuarios usuarios) {
		
		String senha = usuarios.getSenha();
		String senhaCrypto = new BCryptPasswordEncoder().encode(senha);
		usuarios.setSenha(senhaCrypto);
		
		Papel papel = pr.findByNome("ROLE_USUARIOS");
		List<Papel> papeis = new ArrayList<Papel>();
		papeis.add(papel);
		usuarios.setPapeis(papeis);
		
		
		System.out.println(usuarios);
		
		ur.save(usuarios);
		return "redirect:/";
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView lista() {
		ModelAndView md = new ModelAndView("usuarios/lista");
		
		md.addObject("usuarios",ur.findAll());
		
		return md;
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView selecionarUsuarios(@PathVariable Long id) {
		ModelAndView md = new ModelAndView();
		
		Optional<Usuarios> optional = ur.findById(id);
		
		if(optional.isEmpty()) {
			md.setViewName("redirect:/usuarios");
			return md;
			
			
		}
		md.setViewName("usuarios/edit");
		md.addObject("usuario", optional.get());
		md.addObject("papeis", pr.findAll());
		return md;
	}
}
