package consultorio.gestion_turnos.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import consultorio.gestion_turnos.dto.AvailabilityDto;
import consultorio.gestion_turnos.entities.Availability;
import consultorio.gestion_turnos.entities.Professional;
import consultorio.gestion_turnos.entities.User;
import consultorio.gestion_turnos.repositories.AppointmentRepository;
import consultorio.gestion_turnos.repositories.AvailabilityRepository;
import consultorio.gestion_turnos.repositories.ProfessionalRepository;
import consultorio.gestion_turnos.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AvailabilityService {
    private AvailabilityRepository availabilityRepository;
    private ProfessionalRepository professionalRepository;
    private UserRepository userRepository;
    private AppointmentRepository appointmentRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository, ProfessionalRepository professionalRepository, UserRepository userRepository, AppointmentRepository appointmentRepository) {
        this.availabilityRepository = availabilityRepository;
        this.professionalRepository = professionalRepository;
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public void setAvailability(List<AvailabilityDto> dtos) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
            .orElseThrow(()-> new EntityNotFoundException("User not found"));

        Professional professional = professionalRepository.findByUserId(user.getId())
            .orElseThrow(()-> new EntityNotFoundException("Professional not found"));

        List<Availability> newAvailabilities = dtos.stream().map(dto->{
            Availability a = new Availability();
            a.setProfessional(professional);
            a.setDayOfWeek(dto.getDayOfWeek());
            a.setStartTime(dto.getStartTime());
            a.setEndTime(dto.getEndTime());
            return a;
        }).toList();

        availabilityRepository.saveAll(newAvailabilities);
    }

    public List<AvailabilityDto> getAvailabilities(Long professionalId) {
        List<Availability> list = availabilityRepository.findByProfessionalId(professionalId);

        return list.stream().map(element -> {
            AvailabilityDto dto = new AvailabilityDto();
            dto.setDayOfWeek(element.getDayOfWeek());
            dto.setStartTime(element.getStartTime());
            dto.setEndTime(element.getEndTime());
            return dto;
        }).toList();
    }

    public List<LocalTime> getTimeSlots(Long professionalId, LocalDate date) {
        DayOfWeek dow = date.getDayOfWeek();

        Availability availability = availabilityRepository.findByProfessionalIdAndDayOfWeek(professionalId, dow)
            .orElseThrow(()-> new EntityNotFoundException("Professional not available in this date"));
        
        List<LocalTime> slots = new ArrayList<>();
        LocalTime current = availability.getStartTime();

        while(!current.isAfter(availability.getEndTime())) {
            if(!appointmentRepository.existsByProfessionalIdAndDateAndTime(professionalId, date, current)) {
                slots.add(current);
            }
            current = current.plusMinutes(20);
        }
        return slots;
    }
}
