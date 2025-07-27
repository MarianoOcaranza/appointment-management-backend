package consultorio.gestion_turnos.repositories;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import consultorio.gestion_turnos.entities.Availability;

public interface AvailabilityRepository extends JpaRepository<Availability, Long>{
    List<Availability> findByProfessionalId(Long professionalId);
    List<Availability> findByProfessionalIdAndDayOfWeek(Long professionalId, DayOfWeek dayOfWeek);
    void deleteByProfessionalId(Long professionalId);
}
