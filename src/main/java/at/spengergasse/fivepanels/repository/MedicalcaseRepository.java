package at.spengergasse.fivepanels.repository;

import at.spengergasse.fivepanels.model.medicalcase.Medicalcase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface MedicalcaseRepository extends JpaRepository<Medicalcase, UUID> {

    Optional<Medicalcase> findById();
}
