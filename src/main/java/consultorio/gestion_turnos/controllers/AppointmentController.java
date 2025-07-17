package consultorio.gestion_turnos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import consultorio.gestion_turnos.dto.AppointmentRequestDto;
import consultorio.gestion_turnos.services.AppointmentService;

@RestController
@RequestMapping("/api/patient-appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/create")
    private ResponseEntity<?> createAppointment(@RequestBody AppointmentRequestDto dto) {
        try {
            appointmentService.createAppointment(dto);
            return ResponseEntity.ok("Appointment scheduled successfully");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
