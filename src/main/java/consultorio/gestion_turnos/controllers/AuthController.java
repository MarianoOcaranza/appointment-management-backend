package consultorio.gestion_turnos.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import consultorio.gestion_turnos.dto.PatientRegisterDto;
import consultorio.gestion_turnos.dto.UserLoginDto;
import consultorio.gestion_turnos.security.JwtUtils;
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

//------------------------------Register patient endpoint /api/auth/register---------------------------------
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody PatientRegisterDto dto, BindingResult bindingValidations) {

        if (bindingValidations.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingValidations.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getField() + error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            userService.registrarPaciente(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Patient registered successfully");
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

            UserDetails userDetails = userService.loadUserByUsername(dto.getUsername());
            String token = jwtUtils.generateToken(userDetails);
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false); //activar para HTTPS
            cookie.setPath("/");
            cookie.setMaxAge(24*60*60);
            response.addCookie(cookie);

            return ResponseEntity.ok("Login successful, welcome " + jwtUtils.getUsernameFromToken(token) + " Role: " + jwtUtils.getRoleFromToken(token));

        } catch(AuthenticationException e) {
            return ResponseEntity.status(403).body("Invalid credentials: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Unexpected error: " + e.getMessage());
        }
    }
}
