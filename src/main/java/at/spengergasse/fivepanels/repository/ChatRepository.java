package at.spengergasse.fivepanels.repository;

import at.spengergasse.fivepanels.model.messenger.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Long, Chat> {
}
