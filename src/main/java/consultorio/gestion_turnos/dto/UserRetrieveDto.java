package consultorio.gestion_turnos.dto;

import consultorio.gestion_turnos.enums.Role;

public class UserRetrieveDto {
    public String fullName;
    public String email;
    public Role role;

    public UserRetrieveDto(String fullName, String email, Role role) {
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }
}
