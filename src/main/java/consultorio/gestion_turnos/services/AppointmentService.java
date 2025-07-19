package consultorio.gestion_turnos.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import consultorio.gestion_turnos.dto.AppointmentRequestDto;
import consultorio.gestion_turnos.dto.AppointmentRetrieveDto;
import consultorio.gestion_turnos.entities.Appointment;
import consultorio.gestion_turnos.entities.Patient;
import consultorio.gestion_turnos.entities.Professional;
import consultorio.gestion_turnos.entities.User;
import consultorio.gestion_turnos.enums.Role;
import consultorio.gestion_turnos.repositories.AppointmentRepository;
import consultorio.gestion_turnos.repositories.PatientRepository;
import consultorio.gestion_turnos.repositories.ProfessionalRepository;
import consultorio.gestion_turnos.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;
    private ProfessionalRepository professionalRepository;
    private UserRepository userRepository;
    private PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, ProfessionalRepository professionalRepository, UserRepository userRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.professionalRepository = professionalRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
    }

    public void createAppointment(AppointmentRequestDto dto) throws Exception {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if(!user.getRole().equals(Role.PACIENTE)) {
            throw new Exception("User is not a patient");
        }

        Patient patient = patientRepository.findByUserId(user.getId())
            .orElseThrow(()-> new EntityNotFoundException("Patient not found"));

        Professional professional = professionalRepository.findById(dto.getProfessionalId())
            .orElseThrow(()-> new EntityNotFoundException("Professional not found"));
       
        if (appointmentRepository.existsByProfessionalAndDateTime(professional, dto.getDateTime())) {
            throw new Exception("Appointment is not available");
        }
        
        Appointment appointment = new Appointment();
        appointment.setProfessional(professional);
        appointment.setPatient(patient);
        appointment.setDateTime(dto.getDateTime());
        
        appointmentRepository.save(appointment);
    }

    //Instead of deleting, I would prefer 'cancelling' appointments. But that will be a future implementation
    public void deleteAppointment(Long id) throws Exception {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Appointment not found"));
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!appointment.getPatient().getUser().getUsername().equals(principal)) {
            throw new Exception("This appointment is not yours!!!");
        }
        
        appointmentRepository.delete(appointment);
    }

    @Transactional
    public List<AppointmentRetrieveDto> getPatientAppointments() {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Patient patient = patientRepository.findByUserId(user.getId()).orElseThrow(()-> new EntityNotFoundException("User is not a patient"));
        
        return patient.getAppointments().stream()
            .map(appointment -> new AppointmentRetrieveDto(
                appointment.getId(),
                appointment.getProfessional().getUser().getUsername(),
                appointment.getPatient().getUser().getUsername(),
                appointment.getDateTime()
            )).collect(Collectors.toList());
    }


    @Transactional
    public List<AppointmentRetrieveDto> getProfessionalAppointments() {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Professional professional = professionalRepository.findByUserId(user.getId()).orElseThrow(()-> new EntityNotFoundException("User is not a professional"));

        return professional.getAppointments().stream()
            .map(appointment -> new AppointmentRetrieveDto(
                appointment.getId(),
                appointment.getProfessional().getUser().getUsername(),
                appointment.getPatient().getUser().getUsername(),
                appointment.getDateTime()
            )).collect(Collectors.toList());
    }


}
