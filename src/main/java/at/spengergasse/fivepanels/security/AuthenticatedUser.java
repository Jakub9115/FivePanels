package at.spengergasse.fivepanels.security;

import at.spengergasse.fivepanels.data.UserRepository;
import at.spengergasse.fivepanels.model.doctor.Doctor;
import at.spengergasse.fivepanels.repository.DoctorRepository;
import com.vaadin.flow.spring.security.AuthenticationContext;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;

@Component
public class AuthenticatedUser {

    private final DoctorRepository doctorRepository;
    private final AuthenticationContext authenticationContext;

    public AuthenticatedUser(AuthenticationContext authenticationContext, DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
        this.authenticationContext = authenticationContext;
    }

    @Transactional
    public Optional<Doctor> get() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
                .map(userDetails -> doctorRepository.findByEmail(userDetails.getUsername()));
    }

    public void logout() {
        authenticationContext.logout();
    }

}
