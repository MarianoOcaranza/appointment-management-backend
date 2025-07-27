package consultorio.gestion_turnos.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import consultorio.gestion_turnos.dto.AvailabilityDto;
import consultorio.gestion_turnos.entities.Appointment;
import consultorio.gestion_turnos.entities.Availability;
import consultorio.gestion_turnos.entities.Professional;
import consultorio.gestion_turnos.entities.User;
import consultorio.gestion_turnos.repositories.AppointmentRepository;
import consultorio.gestion_turnos.repositories.AvailabilityRepository;
import consultorio.gestion_turnos.repositories.ProfessionalRepository;
import consultorio.gestion_turnos.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

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

    @Transactional
    public void setAvailability(List<AvailabilityDto> dtos) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
            .orElseThrow(()-> new EntityNotFoundException("User not found"));

        Professional professional = professionalRepository.findByUserId(user.getId())
            .orElseThrow(()-> new EntityNotFoundException("You are not registered as a professional"));

        availabilityRepository.deleteByProfessionalId(professional.getId()); //Delete previous availabilities

        List<Availability> newAvailabilities = dtos.stream().map(dto->{
            if(dto.getStartTime().isAfter(dto.getEndTime())) {
                throw new IllegalArgumentException("Start time cannot be ahead end time");
            }
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

    public List<LocalTime> getTimeSlots(Long professionalId, LocalDate date) throws Exception {
        DayOfWeek dow = date.getDayOfWeek();

        List<Availability> availability = availabilityRepository.findByProfessionalIdAndDayOfWeek(professionalId, dow);

        if (availability.isEmpty()) {
            throw new Exception("Professional not available on this date");
        }
        
        Set<LocalTime> occupied = appointmentRepository.findByProfessionalIdAndDate(professionalId, date)
            .stream()
            .map(Appointment::getTime)
            .collect(Collectors.toSet());

        List<LocalTime> slots = new ArrayList<>();

        for (Availability a : availability) {
            LocalTime current = a.getStartTime();
            while (current.plusMinutes(20).isBefore(a.getEndTime()) || current.plusMinutes(20).equals(a.getEndTime())) {
                if (!occupied.contains(current)) {
                    slots.add(current);
                }
                current = current.plusMinutes(20);
            }
        }
        return slots;
    }
}
