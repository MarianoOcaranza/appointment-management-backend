package consultorio.gestion_turnos.services;

import consultorio.gestion_turnos.dto.PatientRegisterDto;
import consultorio.gestion_turnos.dto.ProfessionalRegisterDto;
import consultorio.gestion_turnos.entities.Patient;
import consultorio.gestion_turnos.entities.Professional;
import consultorio.gestion_turnos.entities.User;
import consultorio.gestion_turnos.enums.Role;
import java.time.LocalDateTime;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import consultorio.gestion_turnos.repositories.PatientRepository;
import consultorio.gestion_turnos.repositories.ProfessionalRepository;
import consultorio.gestion_turnos.repositories.UserRepository;
import consultorio.gestion_turnos.security.UserDetailsImpl;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final ProfessionalRepository professionalRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PatientRepository patientRepository, ProfessionalRepository professionalRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.professionalRepository = professionalRepository;
        this.passwordEncoder = passwordEncoder;
    }


//------------------------------Register patient---------------------------------
    public void registerPatient(PatientRegisterDto dto) throws Exception {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new Exception("Email already in use");
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new Exception("Username already exists");
        }
        if (patientRepository.existsByInsuranceNumber(dto.getInsuranceNumber())) {
            throw new Exception("Insurance number is already registered");
        }
        
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setRole(Role.PATIENT);
        user.setActive(true);
        user.setUpDateTime(LocalDateTime.now());

        Patient patient = new Patient();
        patient.setUser(user);
        patient.setInsuranceNumber(dto.getInsuranceNumber());
        
        userRepository.save(user);
        patientRepository.save(patient);
    }


//------------------------------Register professional---------------------------------
    public void registerProfessional(ProfessionalRegisterDto dto) throws Exception {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new Exception("Email already in use");
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new Exception("Username already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setRole(Role.PROFESSIONAL);
        user.setActive(true);
        user.setUpDateTime(LocalDateTime.now());

        Professional professional = new Professional();
        professional.setSpecialty(dto.getSpecialty());
        professional.setUser(user);

        userRepository.save(user);
        professionalRepository.save(professional);
    }
    
//------------------------------Deactivate user---------------------------------
    public void deactivateUser(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User does not exists.");
        } else if (!user.getActive()) {
            throw new UsernameNotFoundException("Username is already unactive");
        }
        user.setActive(false);
        userRepository.save(user);
    }


//----------------------Load by username (UserDetailsService)--------------------------
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{   
        User user = userRepository.findByUsername(username);
        if(user == null ) {
            throw new UsernameNotFoundException("User does not exists");
        }
        if (!user.getActive()) {
            throw new UsernameNotFoundException("User is not active");
        }
        return new UserDetailsImpl(user);
    }       
}
