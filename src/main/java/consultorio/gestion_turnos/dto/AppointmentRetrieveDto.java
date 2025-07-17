package consultorio.gestion_turnos.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AppointmentRetrieveDto {
    public Long professionalId;
    public Long patientId;
    public LocalDateTime localDateTime;
}
