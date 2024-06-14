package at.spengergasse.fivepanels.service;

import at.spengergasse.fivepanels.model.doctor.Doctor;
import at.spengergasse.fivepanels.repository.DoctorRepository;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private DoctorRepository repo;

    public DoctorService(DoctorRepository repo) {
        this.repo = repo;
    }

    public void save(Doctor doctor) {
        repo.save(doctor);
    }
}
