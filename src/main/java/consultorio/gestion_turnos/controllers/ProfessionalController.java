package consultorio.gestion_turnos.controllers;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import consultorio.gestion_turnos.dto.ProfessionalRetrieveDto;
import consultorio.gestion_turnos.services.ProfessionalService;

@RestController
@RequestMapping("/api/professionals")
public class ProfessionalController {
    private ProfessionalService professionalService;
    
    public ProfessionalController(ProfessionalService professionalService) {
        this.professionalService = professionalService;
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
}
