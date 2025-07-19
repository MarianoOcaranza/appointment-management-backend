package consultorio.gestion_turnos.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AppointmentRetrieveDto {
    public Long id;
    public String professionalUsername;
    public String patientUsername;
    public LocalDateTime dateTime;

    public AppointmentRetrieveDto(Long id, String professionalUsername, String patientUsername, LocalDateTime dateTime) {
        this.id = id;
        this.professionalUsername = professionalUsername;
        this.patientUsername = patientUsername;
        this.dateTime = dateTime;
    }
}
