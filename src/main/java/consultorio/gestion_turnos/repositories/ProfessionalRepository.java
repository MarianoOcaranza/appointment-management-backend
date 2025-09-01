package consultorio.gestion_turnos.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import consultorio.gestion_turnos.dto.ProfessionalRetrieveDto;
import consultorio.gestion_turnos.entities.Professional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long>{
        Optional<Professional> findByUserId(Long userId);
        boolean existsByMatriculaNac(Long matriculaNac);
        boolean existsByMatriculaProv(Long matriculaProv);

        @Query("""
                SELECT new consultorio.gestion_turnos.dto.ProfessionalRetrieveDto(
                p.id, u.firstName, u.lastName, u.phone, u.email, p.specialty, u.provincia, u.localidad, p.matriculaNac, p.matriculaProv, p.modalidad
                )
                FROM Professional p
                JOIN p.user u
                WHERE u.active = true
        """)
        Page<ProfessionalRetrieveDto> findAllPage(Pageable pageable);


        @Query("""
                SELECT new consultorio.gestion_turnos.dto.ProfessionalRetrieveDto(
                p.id, u.firstName, u.lastName, u.phone, u.email, p.specialty, u.provincia, u.localidad, p.matriculaNac, p.matriculaProv, p.modalidad
                )
                FROM Professional p
                JOIN p.user u
                WHERE LOWER(p.specialty) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(u.provincia) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(u.localidad) LIKE LOWER(CONCAT('%', :search, '%'))
                AND u.active = true
                """)
        Page<ProfessionalRetrieveDto> searchProfessional(@Param("search") String search, Pageable pageable);
}
