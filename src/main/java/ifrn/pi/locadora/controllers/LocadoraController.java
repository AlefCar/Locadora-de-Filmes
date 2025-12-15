// Define o pacote onde este controller está localizado
package ifrn.pi.locadora.controllers;

// Importa a entidade Filme (modelo que representa um filme no sistema)
import ifrn.pi.locadora.models.Filme;

// Importações para trabalhar com listas e opcionais
import java.util.List;
import java.util.Optional;

// Importações do Spring Framework
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// Importa o repositório responsável pelo acesso ao banco de dados
import ifrn.pi.locadora.repositories.FilmeRepository;

// Indica que esta classe é um Controller do Spring MVC
@Controller
// Define a rota base para todas as URLs deste controller
@RequestMapping("/locadora")
public class LocadoraController {

    // Injeta automaticamente o repositório FilmeRepository
    // Ele será usado para salvar, buscar e remover filmes do banco
	@Autowired
	private FilmeRepository lr;

	// =========================
	// EXIBE O FORMULÁRIO
	// =========================
	
	// Mapeia requisições GET para /locadora/form
	@GetMapping("/form")
	public ModelAndView form() {
	    // Cria um ModelAndView apontando para o template "locadora/formFilme"
	    ModelAndView mv = new ModelAndView("locadora/formFilme");
	    
	    // Adiciona um objeto Filme vazio para ser usado no formulário
	    mv.addObject("filme", new Filme());
	    
	    // Retorna a view com os dados
	    return mv;
	}

	// =========================
	// SALVA UM FILME
	// =========================
	
	// Mapeia requisições POST para /locadora
	@PostMapping
	public String salvar(Filme filme) {

		// Exibe o objeto Filme no console (útil para testes)
		System.out.println(filme);
		
		// Salva o filme no banco de dados
		lr.save(filme);

		// Redireciona para a listagem de filmes
		return "redirect:/locadora";
	}
	
	// =========================
	// LISTA TODOS OS FILMES
	// =========================
	
	// Mapeia requisições GET para /locadora
	@GetMapping
	public ModelAndView listar() {
		
		// Busca todos os filmes do banco
		List<Filme> locadora = lr.findAll();
		
		// Define a view que exibirá a lista
		ModelAndView mv = new ModelAndView("locadora/lista");
		
		// Envia a lista de filmes para a view
		mv.addObject("locadora", locadora);
		
		// Retorna a página com os dados
		return mv;
	}
	
	// =========================
	// DETALHES DE UM FILME
	// =========================
	
	// Mapeia GET para /locadora/{id}/detalhes
    @GetMapping("/{id}/detalhes")
    public String detalhes(@PathVariable("id") Long id, Model model) {
        
        // Busca o filme pelo ID
        // Se não existir, retorna null
        Filme filme = lr.findById(id).orElse(null);
        
        // Envia o filme para a view
        model.addAttribute("filme", filme);
        
        // Retorna a página de detalhes
        return "locadora/detalhes";
    }
	
	// =========================
	// EDITAR / SELECIONAR FILME
	// =========================
	
	// Restringe o acesso apenas para usuários com o papel ATENDENTE
	@PreAuthorize("hasRole('ATENDENTE')")
	// Mapeia GET para /locadora/{id}/selecionar
	@GetMapping("/{id}/selecionar")
	public ModelAndView selecionarEvento(@PathVariable Long id) {
	    
	    ModelAndView md = new ModelAndView();
	    
	    // Busca o filme pelo ID
	    Optional<Filme> opt = lr.findById(id);
	    
	    // Se o filme não existir, redireciona para a lista
	    if(opt.isEmpty()) {
	        md.setViewName("redirect:/locadora");
	        return md;
	    }
	    
	    // Se existir, abre o formulário preenchido
	    md.setViewName("locadora/formFilme");
	    
	    // Obtém o filme encontrado
	    Filme locadora = opt.get();
	    
	    // Envia o filme para o formulário
	    md.addObject("filme", locadora);
	    
	    return md;
	}

	// =========================
	// REMOVER FILME
	// =========================
	
	// Apenas usuários ADMIN podem remover filmes
	@PreAuthorize("hasRole('ADMIN')")
	// Mapeia GET para /locadora/{id}/remover
	@GetMapping("/{id}/remover")
	public String apagarEvento(@PathVariable Long id) {
		
		// Busca o filme pelo ID
		Optional<Filme> opt = lr.findById(id);
		
		// Se existir, remove do banco
		if(!opt.isEmpty()) {
			Filme locadora = opt.get();
			lr.delete(locadora);
		}
		
		// Redireciona para a lista
		return "redirect:/locadora";
	}

	// =========================
	// CONTROLLER DE COMPRA
	// =========================
	
	// Controller para comprar filme
	@Controller
	public class ComprarController {

	    // Mapeia GET para /comprar
	    @GetMapping("/comprar")
	    public String comprar() {
	        
	        // Retorna a página comprar.html
	        return "/comprar.html";
	    }
	}
}