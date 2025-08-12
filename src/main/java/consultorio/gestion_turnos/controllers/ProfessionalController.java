package consultorio.gestion_turnos.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import consultorio.gestion_turnos.dto.AvailabilityDto;
import consultorio.gestion_turnos.dto.ProfessionalRetrieveDto;
import consultorio.gestion_turnos.services.AvailabilityService;
import consultorio.gestion_turnos.services.ProfessionalService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/professionals")
public class ProfessionalController {
    private ProfessionalService professionalService;
    private AvailabilityService availabilityService;
    
    public ProfessionalController(ProfessionalService professionalService, AvailabilityService availabilityService) {
        this.professionalService = professionalService;
        this.availabilityService = availabilityService;
    }


//----------------Get/Search professionals endpoint /api/professionals/?search=----------------------
    @GetMapping
    public ResponseEntity<?> getProfessionals(
                            @RequestParam(required = false) String search,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);

        try {
            if(search != null && !search.isEmpty()) {
                List<ProfessionalRetrieveDto> professionalPage = professionalService.searchProfessionals(search, pageable);
                return ResponseEntity.ok(professionalPage);
            } else {
                List<ProfessionalRetrieveDto> professionalPage = professionalService.getProfessionals(pageable);
                return ResponseEntity.ok(professionalPage);
            }
        } catch(Exception e) {
            return ResponseEntity.internalServerError().body("An error has occurred:" + e.getMessage());
        }
    }


//----------------Set availability endpoint /api/professionals/availability----------------------
    @PostMapping("/availability")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity<?> setAvailability(@RequestBody List<AvailabilityDto> dtos) {
        try {
            availabilityService.setAvailability(dtos);
            return ResponseEntity.ok("Availabilities configured successfully");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


//----------------Get professional availability endpoint /api/professionals/{id}/availability----------------------
    @GetMapping("/{id}/availability")
    public ResponseEntity<?> getAvailability(@PathVariable Long id) {                                  
        try {
            List<AvailabilityDto> availabilities = availabilityService.getAvailabilities(id);
            return ResponseEntity.ok(availabilities);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body("Problem: " + e.getMessage());
        }
    } 


//----------------Get timeslots for professional endpoint /api/professionals/{id}/availability/slots/?date=----------------------
    @GetMapping("/{id}/availability/slots")
    public ResponseEntity<?> getSlots(@PathVariable Long id, @RequestParam LocalDate date) {
        try {
            List<LocalTime> slots = availabilityService.getTimeSlots(id, date);
            return ResponseEntity.ok(slots);
        } catch(Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
