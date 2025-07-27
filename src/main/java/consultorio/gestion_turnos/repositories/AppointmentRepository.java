package consultorio.gestion_turnos.repositories;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;

import consultorio.gestion_turnos.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
    boolean existsByProfessionalIdAndDateAndTime(Long professionalId, LocalDate date, LocalTime time);
}
