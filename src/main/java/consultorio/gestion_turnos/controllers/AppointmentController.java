package consultorio.gestion_turnos.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import consultorio.gestion_turnos.dto.AppointmentRequestDto;
import consultorio.gestion_turnos.dto.AppointmentRetrieveDto;
import consultorio.gestion_turnos.services.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping
    private ResponseEntity<?> createAppointment(@RequestBody AppointmentRequestDto dto) {
        try {
            appointmentService.createAppointment(dto);
            return ResponseEntity.ok("Appointment scheduled successfully");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    private List<AppointmentRetrieveDto> getAppointments() throws Exception {
        return appointmentService.getAppointments();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        try {
            appointmentService.deleteAppointment(id);
            return ResponseEntity.ok("Appointment cancelled successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
