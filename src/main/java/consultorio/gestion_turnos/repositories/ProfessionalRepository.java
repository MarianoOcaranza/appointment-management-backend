package consultorio.gestion_turnos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import consultorio.gestion_turnos.entities.Professional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long>{
        Optional<Professional> findByUserId(Long userId);

}
