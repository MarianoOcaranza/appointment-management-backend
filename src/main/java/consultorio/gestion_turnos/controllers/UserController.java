package consultorio.gestion_turnos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import consultorio.gestion_turnos.dto.UserRetrieveDto;
import consultorio.gestion_turnos.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//------------------------------Deactivate user endpoint /api/users/me---------------------------------
    @PatchMapping("/me")
    public ResponseEntity<?> deactivateUser() {
        try {
            userService.deactivateUser();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("User deactivated successfully");
    }


//------------------------------Get current user endpoint /api/users/me---------------------------------
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        try {
            UserRetrieveDto dto = userService.getCurrentUser(authentication);
            return ResponseEntity.ok(dto);
        } catch(Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving user info: " + e.getMessage());
        }
    }
}
