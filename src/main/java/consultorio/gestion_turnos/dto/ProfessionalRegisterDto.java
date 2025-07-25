package consultorio.gestion_turnos.dto;

import consultorio.gestion_turnos.enums.Modalidad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProfessionalRegisterDto extends UserRegisterDto{
    @NotBlank(message = "Professional must have a specialty")
    private String specialty;
    
    private String matriculaNac;
    private String matriculaProv;

    @NotNull(message = "Please, set a modality")
    private Modalidad modalidad;
}
