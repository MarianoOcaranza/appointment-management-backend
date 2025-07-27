package consultorio.gestion_turnos.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class AppointmentRetrieveDto {
    public Long id;
    public String professionalUsername;
    public String patientUsername;
    public LocalDate date;
    public LocalTime time;

    public AppointmentRetrieveDto(Long id, String professionalUsername, String patientUsername, LocalDate date, LocalTime time) {
        this.id = id;
        this.professionalUsername = professionalUsername;
        this.patientUsername = patientUsername;
        this.date = date;
        this.time = time;
    }
}
