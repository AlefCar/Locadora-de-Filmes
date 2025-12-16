// Define o pacote onde está a configuração de segurança
package ifrn.pi.locadora.config;

// Importações do Spring Framework e Spring Security
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Indica que esta classe é uma classe de configuração
@Configuration
// Habilita a segurança web do Spring Security
@EnableWebSecurity
// Habilita o uso de @PreAuthorize nos métodos
@EnableMethodSecurity
public class WebSecurityConfig {
	
	// =========================
	// CONFIGURAÇÃO DE SEGURANÇA
	// =========================
	
	// Define o filtro de segurança da aplicação
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			// =========================
			// REGRAS DE AUTORIZAÇÃO
			// =========================
			.authorizeHttpRequests((authorize) -> authorize
				
				// Permite acesso livre à página inicial "/"
				.requestMatchers("/").permitAll()
				
				// Permite acesso livre ao formulário de cadastro de usuários
				.requestMatchers("/usuarios/form").permitAll()
				
				// Permite envio de formulário (POST) para cadastro de usuários
				.requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
				
				// Qualquer outra rota exige autenticação
				.anyRequest().authenticated()
			)

			// =========================
			// CONFIGURAÇÃO DE LOGIN
			// =========================
			.formLogin((formLogin) -> formLogin
				
				// Define a página de login personalizada
				.loginPage("/login")
				
				// Permite acesso à página de login para todos
				.permitAll()
			)

			// =========================
			// CONFIGURAÇÃO DE LOGOUT
			// =========================
			.logout(logout -> logout
				
				// Permite que qualquer usuário faça logout
				.permitAll()
			);

		// Constrói e retorna a cadeia de filtros de segurança
		return http.build();
	}
	
	// =========================
	// CODIFICADOR DE SENHA
	// =========================
	
	// Define o algoritmo de criptografia de senha da aplicação
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		// Usa BCrypt para criptografar senhas (recomendado)
		return new BCryptPasswordEncoder();
	}
}
