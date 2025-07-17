package consultorio.gestion_turnos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterDto {

    @NotBlank(message="Username required")
    private String username;

    @NotBlank(message = "Email required")
    @Email
    private String email;

    @NotBlank(message = "Password required")
    private String password;

    @NotBlank(message = "Phone required")
    private String phone;

    @NotBlank(message = "First Name required")
    private String firstName;

    @NotBlank(message = "Last Name required")
    private String lastName;
}
