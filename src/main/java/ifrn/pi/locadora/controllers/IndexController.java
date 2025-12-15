// Define o pacote onde o controller está localizado
package ifrn.pi.locadora.controllers;

// Importa a anotação que indica que esta classe é um Controller do Spring MVC
import org.springframework.stereotype.Controller;

// Importa a anotação usada para mapear URLs
import org.springframework.web.bind.annotation.RequestMapping;

// Indica que esta classe é um controller
@Controller
public class IndexController {

	// Mapeia qualquer requisição para a URL raiz "/"
	@RequestMapping("/")
	public String index() {
		
		// Exibe uma mensagem no console quando o método é chamado
		// Serve apenas para debug/teste
		System.out.println("Chamou o método index");
		
		// Redireciona o usuário para a rota /locadora
		// Ou seja, a página inicial do sistema é a listagem de filmes
		return "redirect:/locadora";
	}
}

