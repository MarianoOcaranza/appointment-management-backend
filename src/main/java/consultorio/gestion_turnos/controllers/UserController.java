package consultorio.gestion_turnos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import consultorio.gestion_turnos.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//------------------------------Deactivate user endpoint /api/user/deactivate/{username}---------------------------------
    @PutMapping("/deactivate/{username}")
    public ResponseEntity<?> deactivateUser(@PathVariable String username) {
        try {
            userService.deactivateUser(username);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("User deactivated successfully");
    }
}
