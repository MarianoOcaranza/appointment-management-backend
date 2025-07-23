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

        //The following queries were written in order to solve the N+1 problem
        //This is because all Professionals have an User associated, and some of the User 
        //attributes are incluided in the GET endpoint response. It's much more efficient to retrieve
        //data with DTO directly from the repository, avoiding the access to the 
        //user entity with hibernate.
        @Query("""
                SELECT new consultorio.gestion_turnos.dto.ProfessionalRetrieveDto(
                u.firstName, u.lastName, u.phone, u.email, p.specialty
                )
                FROM Professional p
                JOIN p.user u
        """)
        Page<ProfessionalRetrieveDto> findAllPage(Pageable pageable);


        @Query("""
                SELECT new consultorio.gestion_turnos.dto.ProfessionalRetrieveDto(
                u.firstName, u.lastName, u.phone, u.email, p.specialty
                )
                FROM Professional p
                JOIN p.user u
                WHERE LOWER(p.specialty) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
                """)
        Page<ProfessionalRetrieveDto> searchProfessional(@Param("search") String search, Pageable pageable);
}
