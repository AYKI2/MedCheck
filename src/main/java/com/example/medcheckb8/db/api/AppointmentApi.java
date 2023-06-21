package com.example.medcheckb8.db.api;

import com.example.medcheckb8.db.dto.request.appointment.AddAppointmentRequest;
import com.example.medcheckb8.db.dto.request.appointment.FreeSpecialistRequest;
import com.example.medcheckb8.db.dto.response.AppointmentResponse;
import com.example.medcheckb8.db.dto.response.AppointmentResponseId;
import com.example.medcheckb8.db.dto.response.appointment.AddAppointmentResponse;
import com.example.medcheckb8.db.dto.response.appointment.GetAllAppointmentResponse;
import com.example.medcheckb8.db.dto.response.appointment.ScheduleResponse;
import com.example.medcheckb8.db.dto.response.SimpleResponse;
import com.example.medcheckb8.db.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.medcheckb8.db.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
@CrossOrigin
public class AppointmentApi {
    private final AppointmentService appointmentService;
    private final AppointmentService service;
    private final DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('PATIENT')")
    @Operation(summary = "Метод для добавления новой записи.",
            description = "С помощью этого метода пациент может записаться на прием к врачу. Только для пациентов.")
    public AddAppointmentResponse addAppointment(@RequestBody @Valid AddAppointmentRequest request) {
        return service.save(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Получить все записи на прием.",
            description = "С помощью этого метода администратор может просмотреть все записи на прием. Только для администратора.")
    public List<GetAllAppointmentResponse> getAll(@RequestParam(required = false) String keyWord) {
        return service.getAll(keyWord);
    }

    @PostMapping("/free")
    @PreAuthorize("hasAnyAuthority('PATIENT')")
    @Operation(summary = "Получить ближайшее свободное время.",
            description = "С помощью этого метода пациент может получить ближайшее свободное время. Только для пациентов.")
    public List<ScheduleResponse> getTheNearestFreeDoctors(@RequestBody @Valid FreeSpecialistRequest request) {
        return doctorService.findDoctorsByDate(request.department(), request.timeZone());
    }

    @PostMapping("/canceled")
    @PreAuthorize("hasAnyAuthority('PATIENT')")
    @Operation(summary = "Отменить запись.",
            description = "С помощью этого метода пациент может отменить запись к врачу. Только для пациентов.")
    public SimpleResponse canceled(@RequestParam Long appointmentId) {
        return service.canceled(appointmentId);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','PATIENT')")
    @Operation(summary = "Удалить записи.",
            description = "Этот метод может быть использован как администратором, так и пациентом. Если используется как пациент, " +
                    "то необходимо передать 'id', так как пациент может удалить только все записи, в то время как администратор " +
                    "может удалять их по одной или все сразу.")
    public SimpleResponse delete(@RequestBody(required = false) List<Long> appointments) {
        return service.delete(appointments);
    }

    @GetMapping("/myAppointments")
    @PreAuthorize("hasAnyAuthority('PATIENT')")
    @Operation(summary = "Получить ваши записи.",
            description = "С помощью этого метода пациент может получить все свои записи. Только для пациентов.")
    public List<AppointmentResponse> myAppointments() {
        return appointmentService.getUserAppointments();
    }

    @GetMapping("/myAppointment")
    @PreAuthorize("hasAnyAuthority('PATIENT')")
    @Operation(summary = "Получить ваши записи по id.",
            description = "С помощью этого метода пациент может свою записи. Только для пациентов.")
    public AppointmentResponseId myAppointmentById(@RequestParam Long id) {
        return appointmentService.getUserAppointmentById(id);
    }
}
