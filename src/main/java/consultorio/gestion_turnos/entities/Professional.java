package consultorio.gestion_turnos.entities;

import java.util.ArrayList;
import java.util.List;

import consultorio.gestion_turnos.enums.Modalidad;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="professionals")
public class Professional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String specialty;

    private String matriculaNac;
    private String matriculaProv;
    private Modalidad modalidad;

    private String lastName;
    
    @OneToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "professional")
    private List<Appointment> appointments = new ArrayList<>();
    
}
