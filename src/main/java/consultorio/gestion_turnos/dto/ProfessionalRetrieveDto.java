package consultorio.gestion_turnos.dto;

public class ProfessionalRetrieveDto {
    public String firstName;
    public String lastName;
    public String phone;
    public String email;
    public String specialty;

    public ProfessionalRetrieveDto(String firstName, String lastName, String phone, String email, String specialty) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.specialty = specialty;
    }
}
