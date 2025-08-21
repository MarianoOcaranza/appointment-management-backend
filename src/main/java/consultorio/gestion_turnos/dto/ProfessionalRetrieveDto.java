package consultorio.gestion_turnos.dto;

import consultorio.gestion_turnos.enums.Modalidad;
import lombok.Data;

@Data
public class ProfessionalRetrieveDto {
    public String firstName;
    public String lastName;
    public String phone;
    public String email;
    public String specialty;
    public String provincia;
    public String localidad;
    public Long matriculaProv;
    public Long matriculaNac;
    public Modalidad modalidad;

    public ProfessionalRetrieveDto(String firstName, String lastName, String phone, String email, String specialty, String provincia, String localidad, Long matriculaNac, Long matriculaProv, Modalidad modalidad) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.specialty = specialty;
        this.provincia = provincia;
        this.localidad = localidad;
        this.matriculaNac = matriculaNac;
        this.matriculaProv = matriculaProv;
        this.modalidad = modalidad;
    }
}
