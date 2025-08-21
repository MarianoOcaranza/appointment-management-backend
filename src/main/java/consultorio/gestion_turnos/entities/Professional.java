package consultorio.gestion_turnos.entities;

import java.util.ArrayList;
import java.util.List;
import consultorio.gestion_turnos.enums.Modalidad;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Column(nullable = false)
    private String specialty;

    @Column(nullable = false, unique = true)
    private Long matriculaNac;

    @Column(unique = true)
    private Long matriculaProv;

    @Enumerated(EnumType.STRING)
    private Modalidad modalidad;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "professional")
    private List<Appointment> appointments = new ArrayList<>();
    
}
