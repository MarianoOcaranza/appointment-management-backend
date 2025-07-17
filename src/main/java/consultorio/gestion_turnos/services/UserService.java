package consultorio.gestion_turnos.services;

import consultorio.gestion_turnos.dto.PatientRegisterDto;
import consultorio.gestion_turnos.dto.ProfessionalRegisterDto;
import consultorio.gestion_turnos.dto.UserRegisterDto;
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
public class UserService implements UserDetailsService{
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

    public void registrarPaciente(PatientRegisterDto dto) throws Exception {
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
        user.setRole(Role.PACIENTE);
        user.setActive(true);

        Patient patient = new Patient();
        patient.setUser(user);
        patient.setInsuranceNumber(dto.getInsuranceNumber());
        patient.setUpDateTime(LocalDateTime.now());
        
        userRepository.save(user);
        patientRepository.save(patient);
    }

    public void registrarProfesional(ProfessionalRegisterDto dto) throws Exception {
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
        user.setRole(Role.PROFESIONAL);
        user.setActive(true);

        Professional professional = new Professional();
        professional.setSpecialty(dto.getSpecialty());
        professional.setUser(user);

        professional.setUpDateTime(LocalDateTime.now());

        userRepository.save(user);
        professionalRepository.save(professional);
  
    }
    

    public void registrarAdmin(UserRegisterDto dto) throws Exception {
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
        user.setRole(Role.ADMIN);
        user.setActive(true);

        userRepository.save(user);
    }

    public void deactivateUser(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User does not exists.");
        } else if (!user.getActive()) {
            throw new UsernameNotFoundException("Username is already unactive");
        }
        user.setActive(false);
        
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        } else if (!user.getActive()) {
            throw new UsernameNotFoundException("User is not active!");
        }
        return new UserDetailsImpl(user);
    }       

}
