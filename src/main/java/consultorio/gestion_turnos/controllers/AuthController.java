package consultorio.gestion_turnos.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import consultorio.gestion_turnos.dto.PatientRegisterDto;
import consultorio.gestion_turnos.dto.ProfessionalRegisterDto;
import consultorio.gestion_turnos.dto.UserLoginDto;
import consultorio.gestion_turnos.dto.UserRetrieveDto;
import consultorio.gestion_turnos.security.JwtUtils;
import consultorio.gestion_turnos.security.UserDetailsImpl;
import consultorio.gestion_turnos.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

//------------------------------Register patient endpoint /api/auth/register-patient---------------------------------
    @PostMapping("/patients")
    public ResponseEntity<?> registerPatient(@Valid @RequestBody PatientRegisterDto dto, BindingResult bindingValidations) {

        if (bindingValidations.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingValidations.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getField() + error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            userService.registerPatient(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
        return ResponseEntity.ok("Patient registered successfully");
    }

//------------------------------Register professional endpoint /api/auth/register-professional---------------------------------
    @PostMapping("/professionals")
    public ResponseEntity<?> registerProfessional(@Valid @RequestBody ProfessionalRegisterDto dto, BindingResult bindingValidations) {

        if (bindingValidations.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingValidations.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getField() + error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            userService.registerProfessional(dto);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Professional registered successfully");
    }

//------------------------------Login endpoint /api/auth/login---------------------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto dto, HttpServletResponse response, BindingResult bindingValidations) {

        if (bindingValidations.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingValidations.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getField() + error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String token = jwtUtils.generateToken(userDetails);
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false); //activar para HTTPS
            cookie.setPath("/");
            cookie.setMaxAge(24*60*60);
            response.addCookie(cookie);

            return ResponseEntity.ok(new UserRetrieveDto(userDetails.getFullname(), userDetails.getEmail(), userDetails.getRole()));
        } catch (Exception e) {
            return ResponseEntity.status(200).body(e.getLocalizedMessage());
        }
    }
}
