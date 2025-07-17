package consultorio.gestion_turnos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import consultorio.gestion_turnos.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
