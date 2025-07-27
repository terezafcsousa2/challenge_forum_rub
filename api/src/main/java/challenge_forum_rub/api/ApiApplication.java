package challenge_forum_rub.api;

import challenge_forum_rub.api.usuario.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(ApiApplication.class, args);
	}
	@Bean
	CommandLineRunner init(challenge_forum_rub.api.usuario.UsuarioRepository repository) {
		return args -> {
			System.out.println("Usuários cadastrados: " + repository.count());
		};
	}



}
