package consultorio.gestion_turnos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import consultorio.gestion_turnos.entities.Professional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long>{
}
