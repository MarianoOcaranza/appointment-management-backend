package consultorio.gestion_turnos.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import consultorio.gestion_turnos.dto.ProfessionalRegisterDto;
import consultorio.gestion_turnos.dto.UserRegisterDto;
import consultorio.gestion_turnos.services.UserService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

//------------------------------Register professional endpoint /api/admin/register---------------------------------
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody ProfessionalRegisterDto dto, BindingResult bindingValidations) {

        if (bindingValidations.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingValidations.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getField() + error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            userService.registrarProfesional(dto);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Professional registered successfully");
    }


//------------------------------Register admin endpoint /api/admin/newadmin---------------------------------
    @PostMapping("/newadmin")
    public ResponseEntity<?> newadmin(@Valid @RequestBody UserRegisterDto dto, BindingResult bindingValidations) {
         if (bindingValidations.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingValidations.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getField() + error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            userService.registrarAdmin(dto);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Admin registered succesfully");
    }

//------------------------Deactivate user endpoint /api/admin/deactivate/{username}---------------------------------
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
