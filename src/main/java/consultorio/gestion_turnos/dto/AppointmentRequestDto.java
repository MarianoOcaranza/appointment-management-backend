package consultorio.gestion_turnos.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AppointmentRequestDto {

    @NotBlank(message = "Please select a professional")
    private Long professionalId;

    @NotBlank(message = "Please set a date")
    private LocalDate date;

    @NotBlank(message = "Please set an hour")
    private LocalTime time;
    
}
