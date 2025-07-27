package consultorio.gestion_turnos.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import consultorio.gestion_turnos.dto.UserRetrieveDto;
import consultorio.gestion_turnos.security.UserDetailsImpl;
import consultorio.gestion_turnos.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//------------------------------Deactivate user endpoint /api/user/deactivate/{username}---------------------------------
    @PatchMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deactivateUser() {
        try {
            userService.deactivateUser();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("User deactivated successfully");
    }


//------------------------------Get current user endpoint /api/user/me---------------------------------
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        UserRetrieveDto userDTO = new UserRetrieveDto(userDetails.getFullname(), userDetails.getEmail(), userDetails.getRole());

        return ResponseEntity.ok(userDTO);
    }
}
