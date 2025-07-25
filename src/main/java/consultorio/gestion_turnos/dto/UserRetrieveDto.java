package consultorio.gestion_turnos.dto;

import consultorio.gestion_turnos.enums.Role;

public class UserRetrieveDto {
    public String firstName;
    public String lastName;
    public String email;
    public Role role;

    public UserRetrieveDto(String firstName, String lastName, String email, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }
}
