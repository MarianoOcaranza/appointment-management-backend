package consultorio.gestion_turnos.services;

import consultorio.gestion_turnos.dto.PatientRegisterDto;
import consultorio.gestion_turnos.dto.ProfessionalRegisterDto;
import consultorio.gestion_turnos.dto.UserRetrieveDto;
import consultorio.gestion_turnos.entities.Patient;
import consultorio.gestion_turnos.entities.Professional;
import consultorio.gestion_turnos.entities.User;
import consultorio.gestion_turnos.enums.Role;
import java.time.LocalDateTime;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import consultorio.gestion_turnos.repositories.PatientRepository;
import consultorio.gestion_turnos.repositories.ProfessionalRepository;
import consultorio.gestion_turnos.repositories.UserRepository;
import consultorio.gestion_turnos.security.UserDetailsImpl;
import jakarta.persistence.EntityNotFoundException;


@Service
public class UserService implements UserDetailsService {

//--------------------------Dependencies & injection------------------------------
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

        //---------------------Validates email and usermail existence----------------------
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new Exception("Email already in use");
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new Exception("Username already exists");
        }

        //---------------------User creation------------------------------
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setRole(Role.PATIENT);
        user.setActive(true);
        user.setProvincia(dto.getProvincia());
        user.setLocalidad(dto.getLocalidad());
        user.setUpDateTime(LocalDateTime.now());

        //-------------------Patient creation---------------------------
        Patient patient = new Patient();
        patient.setUser(user);

        //-------------------Save on database---------------------------
        userRepository.save(user);
        patientRepository.save(patient);
    }


//------------------------------Register professional---------------------------------
    public void registerProfessional(ProfessionalRegisterDto dto) throws Exception {

        //---------------------Validates email and username existence---------------------
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new Exception("Email already in use");
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new Exception("Username already exists");
        }

        //-------------------User creation------------------------------
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setRole(Role.PROFESSIONAL);
        user.setActive(true);
        user.setProvincia(dto.getProvincia());
        user.setLocalidad(dto.getLocalidad());
        user.setUpDateTime(LocalDateTime.now());

        //-------------------Professional creation------------------------------
        Professional professional = new Professional();
        professional.setSpecialty(dto.getSpecialty());
        professional.setMatriculaNac(dto.getMatriculaNac());
        professional.setMatriculaProv(dto.getMatriculaProv());
        professional.setUser(user);
        professional.setLastName(dto.getLastName());
        professional.setModalidad(dto.getModalidad());

        //-------------------Save on database---------------------------
        userRepository.save(user);
        professionalRepository.save(professional);
    }


//------------------------------Deactivate user---------------------------------
    public void deactivateUser() throws Exception {

        //---------------------Retrieve current User from username---------------------------------
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
            .orElseThrow(()-> new EntityNotFoundException("User not found"));;

        if (!user.getActive()) {
            throw new Exception("Username is already unactive");
        }

        //Set activated to false
        user.setActive(false);
        userRepository.save(user);
    }
  


//--------------------------------Get current user--------------------------
    public UserRetrieveDto getCurrentUser(Authentication authentication) throws Exception {
        if(authentication == null || !authentication.isAuthenticated()) {
            throw new Exception("User is not authenticated!");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new UserRetrieveDto(userDetails.getUsername(), userDetails.getEmail(), userDetails.getRole());
    }


//----------------------Load by username (UserDetailsService)--------------------------
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username)
            .orElseThrow(()-> new EntityNotFoundException("User does not exists"));

        if (!user.getActive()) {
            throw new UsernameNotFoundException("User is not active");
        }
        
        return new UserDetailsImpl(user);
    }       
}
