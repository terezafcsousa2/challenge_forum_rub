package challenge_forum_rub.api.repository;

import challenge_forum_rub.api.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
}

