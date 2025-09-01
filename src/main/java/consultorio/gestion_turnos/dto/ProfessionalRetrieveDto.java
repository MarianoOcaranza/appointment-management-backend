package consultorio.gestion_turnos.dto;

import consultorio.gestion_turnos.enums.Modalidad;
import lombok.Data;

@Data
public class ProfessionalRetrieveDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String specialty;
    private String provincia;
    private String localidad;
    private Long matriculaProv;
    private Long matriculaNac;
    private Modalidad modalidad;

    public ProfessionalRetrieveDto(Long id, String firstName, String lastName, String phone, String email, String specialty, String provincia, String localidad, Long matriculaNac, Long matriculaProv, Modalidad modalidad) {
        this.id = id;
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
