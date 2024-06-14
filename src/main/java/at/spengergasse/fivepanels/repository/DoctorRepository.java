package at.spengergasse.fivepanels.repository;

import at.spengergasse.fivepanels.model.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

    Optional<Doctor> findById(UUID uuid);

    Doctor findByEmail(String email);

    boolean existsByEmail(String email);
}
