package consultorio.gestion_turnos.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PatientRegisterDto extends UserRegisterDto {

    private String insuranceNumber;
}
