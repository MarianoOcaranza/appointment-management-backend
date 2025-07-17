package consultorio.gestion_turnos.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AppointmentRequestDto {

    @NotBlank(message = "Please select a professional")
    private Long professionalId;

    @NotBlank(message = "Please set a datetime")
    private LocalDateTime dateTime;
}
