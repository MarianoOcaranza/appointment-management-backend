package consultorio.gestion_turnos.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import consultorio.gestion_turnos.dto.AppointmentRequestDto;
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
}
