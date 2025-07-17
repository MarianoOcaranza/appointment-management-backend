package consultorio.gestion_turnos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProfessionalRegisterDto extends UserRegisterDto{
    @NotBlank(message = "Professional must have a specialty")
    private String specialty;
}
