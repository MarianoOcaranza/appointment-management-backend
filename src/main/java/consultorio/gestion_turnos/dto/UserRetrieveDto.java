package consultorio.gestion_turnos.dto;

import consultorio.gestion_turnos.enums.Role;
import lombok.Data;

@Data
public class UserRetrieveDto {
    public String username;
    public String email;
    public Role role;

    public UserRetrieveDto(String username, String email, Role role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
