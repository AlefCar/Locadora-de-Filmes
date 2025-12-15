// Define o pacote onde o controller está localizado
package ifrn.pi.locadora.controllers;

// Importações para trabalhar com listas e opcionais
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Importações do Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

// Importações dos modelos (entidades)
import ifrn.pi.locadora.models.Papel;
import ifrn.pi.locadora.models.Usuarios;

// Importações dos repositórios (acesso ao banco)
import ifrn.pi.locadora.repositories.PapelRepository;
import ifrn.pi.locadora.repositories.UsuariosRepository;

// Indica que a classe é um Controller do Spring MVC
@Controller
// Define a rota base /usuarios para este controller
@RequestMapping("/usuarios")
public class UsuarioController {

	// Injeta o repositório de Papéis (roles/perfis)
	@Autowired
	private PapelRepository pr;

	// Injeta o repositório de Usuários
	@Autowired
	private UsuariosRepository ur;
	
	// =========================
	// FORMULÁRIO DE CADASTRO
	// =========================
	
	// Mapeia GET para /usuarios/form
	@GetMapping("/form")
	public String form() {
		// Retorna a página de formulário de usuário
		return "usuarios/form";
	}
	
	// =========================
	// SALVAR NOVO USUÁRIO
	// =========================
	
	// Mapeia POST para /usuarios
	@PostMapping
	public String salvar(Usuarios usuarios) {
		
		// Obtém a senha digitada no formulário
		String senha = usuarios.getSenha();
		
		// Criptografa a senha usando BCrypt (boa prática de segurança)
		String senhaCrypto = new BCryptPasswordEncoder().encode(senha);
		
		// Substitui a senha em texto puro pela senha criptografada
		usuarios.setSenha(senhaCrypto);
		
		// Busca no banco o papel padrão "ROLE_USUARIOS"
		Papel papel = pr.findByNome("ROLE_USUARIOS");
		
		// Cria uma lista de papéis (roles)
		List<Papel> papeis = new ArrayList<Papel>();
		
		// Adiciona o papel padrão à lista
		papeis.add(papel);
		
		// Define os papéis do usuário
		usuarios.setPapeis(papeis);
		
		// Mostra o usuário no console (debug)
		System.out.println(usuarios);
		
		// Salva o usuário no banco de dados
		ur.save(usuarios);
		
		// Redireciona para a página inicial
		return "redirect:/";
	}
	
	// =========================
	// LISTAR USUÁRIOS
	// =========================
	
	// Mapeia GET para /usuarios
	// Apenas ADMIN pode acessar
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView lista() {
		
		// Define a view da lista de usuários
		ModelAndView md = new ModelAndView("usuarios/lista");
		
		// Envia a lista de usuários para a view
		md.addObject("usuarios", ur.findAll());
		
		return md;
	}
	
	// =========================
	// SELECIONAR USUÁRIO (EDIÇÃO)
	// =========================
	
	// Mapeia GET para /usuarios/{id}
	// Apenas ADMIN pode acessar
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView selecionarUsuarios(@PathVariable Long id) {
		
		ModelAndView md = new ModelAndView();
		
		// Busca o usuário pelo ID
		Optional<Usuarios> optional = ur.findById(id);
		
		// Se não encontrar o usuário, redireciona para a lista
		if(optional.isEmpty()) {
			md.setViewName("redirect:/usuarios");
			return md;
		}
		
		// Se encontrar, abre a página de edição
		md.setViewName("usuarios/edit");
		
		// Envia o usuário selecionado para a view
		md.addObject("usuarios", optional.get());
		
		// Envia todos os papéis disponíveis (para seleção)
		md.addObject("papeis", pr.findAll());
		
		return md;
	}
	
	// =========================
	// SALVAR PAPÉIS DO USUÁRIO
	// =========================
	
	// Mapeia POST para /usuarios/{id}
	// Apenas ADMIN pode acessar
	@PostMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String salvarPapeis(
	        @PathVariable Long id,
	        // Recebe a lista de IDs dos papéis selecionados no formulário
	        @RequestParam(required = false) List<Long> papeisIds
	) {

	    // Busca o usuário pelo ID
	    // Se não existir, lança exceção
	    Usuarios usuarios = ur.findById(id)
	            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

	    // Busca os papéis no banco com base nos IDs recebidos
	    // Se nenhum papel for selecionado, usa uma lista vazia
	    List<Papel> papeis = pr.findAllById(
	            papeisIds == null ? List.of() : papeisIds
	    );

	    // Define os novos papéis do usuário
	    usuarios.setPapeis(papeis);
	    
	    // Salva as alterações no banco
	    ur.save(usuarios);

	    // Redireciona para a lista de usuários
	    return "redirect:/usuarios";
	}
}
