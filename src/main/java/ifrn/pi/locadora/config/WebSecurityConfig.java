package ifrn.pi.locadora.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())  // IMPORTANTE PARA FORM POST FUNCIONAR
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/", "/login", "/usuarios/form").permitAll()
                    .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()

                    // LIBERA FORM DE FILMES PARA ADMIN
                    .requestMatchers(HttpMethod.GET, "/locadora/form").hasRole("ADMIN")

                    // LIBERA SALVAR FILMES PARA ADMIN
                    .requestMatchers(HttpMethod.POST, "/locadora").hasRole("ADMIN")

                    // LISTA FILMES — QUALQUER LOGADO
                    .requestMatchers(HttpMethod.GET, "/locadora").authenticated()

                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login")
                    .permitAll()
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests( (autorize) -> autorize
				.requestMatchers("/").permitAll()
				.requestMatchers("/usuarios/form").permitAll()
				.requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin((formLogin) -> formLogin
				.loginPage("/login")
				.permitAll()
			).logout(logout -> logout.permitAll());

		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

