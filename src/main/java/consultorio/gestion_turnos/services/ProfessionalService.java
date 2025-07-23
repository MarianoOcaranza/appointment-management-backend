package consultorio.gestion_turnos.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import consultorio.gestion_turnos.repositories.ProfessionalRepository;
import consultorio.gestion_turnos.dto.ProfessionalRetrieveDto;

@Service
public class ProfessionalService {
    private ProfessionalRepository professionalRepository;

    public ProfessionalService(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public List<ProfessionalRetrieveDto> getProfessionals(Pageable pageable) {
        Page<ProfessionalRetrieveDto> professionals = professionalRepository.findAllPage(pageable);
        return professionals.getContent();
       
    }

    public List<ProfessionalRetrieveDto> searchProfessionals(String search, Pageable pageable) {
        Page<ProfessionalRetrieveDto> professionals = professionalRepository.searchProfessional(search, pageable);
        return professionals.getContent();
    }
}
