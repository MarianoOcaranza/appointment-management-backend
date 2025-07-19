package consultorio.gestion_turnos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import consultorio.gestion_turnos.dto.AppointmentRetrieveDto;
import consultorio.gestion_turnos.services.AppointmentService;

@RestController
@RequestMapping("/api/professional")
public class ProfessionalController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/appointments")
    private List<AppointmentRetrieveDto> getProfessionalAppointments() {
        return appointmentService.getProfessionalAppointments();
    } 
}
