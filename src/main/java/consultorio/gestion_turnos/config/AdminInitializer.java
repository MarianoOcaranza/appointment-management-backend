package consultorio.gestion_turnos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import consultorio.gestion_turnos.entities.User;
import consultorio.gestion_turnos.enums.Role;
import consultorio.gestion_turnos.repositories.UserRepository;


//This will create an initial user with admin permissions for the first time
@Component
public class AdminInitializer implements CommandLineRunner{
    @Value("${admin.username}")
    private String username;

    @Value("${admin.password}")
    private String password;

    @Value("${admin.email}")
    private String email;

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail(email) && !userRepository.existsByUsername(username)) {
            User admin = new User();
            admin.setUsername(username);
            admin.setEmail(email);
            admin.setFirstName("System");
            admin.setLastName("Admin");
            admin.setPassword(passwordEncoder.encode(password));
            admin.setPhone("N/A");
            admin.setRole(Role.ADMIN);
            admin.setActive(true);

            userRepository.save(admin);

        } else {
            System.out.println("Initial admin user already exists, skipping...");
        }
    }

}
