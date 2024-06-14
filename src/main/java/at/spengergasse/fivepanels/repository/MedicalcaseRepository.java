package at.spengergasse.fivepanels.repository;

import at.spengergasse.fivepanels.model.medicalcase.Medicalcase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalcaseRepository extends JpaRepository<Long, Medicalcase> {
}
