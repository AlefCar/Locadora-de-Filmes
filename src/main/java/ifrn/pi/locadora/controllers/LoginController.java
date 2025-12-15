// Define o pacote onde este controller está localizado
package ifrn.pi.locadora.controllers;

// Importa a anotação que indica que a classe é um Controller do Spring MVC
import org.springframework.stereotype.Controller;

// Importa a anotação para mapear requisições GET
import org.springframework.web.bind.annotation.GetMapping;

// Indica que esta classe é um controller do Spring
@Controller
public class LoginController {

	// =========================
	// PÁGINA DE LOGIN
	// =========================
	
	// Mapeia requisições GET para /login
	@GetMapping("/login")
	public String login() {
		
		// Retorna a página de login
		// Normalmente usada pelo Spring Security
		return "login/login";
	}
	
	// =========================
	// PÁGINA DE LOGOUT
	// =========================
	
	// Mapeia requisições GET para /logout
	@GetMapping("/logout")
	public String logout() {
		
		// Retorna uma página de logout
		// OBS: Em aplicações com Spring Security,
		// o logout geralmente é tratado automaticamente
		return "login/logout";
	}
}
