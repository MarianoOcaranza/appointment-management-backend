package consultorio.gestion_turnos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import consultorio.gestion_turnos.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{
    Optional<Patient> findByUserId(Long userId);
    boolean existsByInsuranceNumber(String insuranceNumber);
}
