package consultorio.gestion_turnos.repositories;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;

import consultorio.gestion_turnos.entities.Appointment;
import consultorio.gestion_turnos.entities.Professional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
    boolean existsByProfessionalAndDateAndTime(Professional professional, LocalDate date, LocalTime time);
}
