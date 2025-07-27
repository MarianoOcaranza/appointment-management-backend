package consultorio.gestion_turnos.repositories;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import consultorio.gestion_turnos.entities.Availability;

public interface AvailabilityRepository extends JpaRepository<Availability, Long>{
    List<Availability> findByProfessionalId(Long professionalId);
    Optional<Availability> findByProfessionalIdAndDayOfWeek(Long professionalId, DayOfWeek dayOfWeek);
}
