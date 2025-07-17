package consultorio.gestion_turnos.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import consultorio.gestion_turnos.entities.Appointment;
import consultorio.gestion_turnos.entities.Professional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
    boolean existsByProfessionalAndDateTime(Professional professional, LocalDateTime dateTime);
}
