    package com.example.medcheckb8.db.dto.response;

    import com.example.medcheckb8.db.enums.Status;
    import lombok.Builder;
    import java.time.LocalDateTime;

    @Builder
    public record AppointmentResponse(
            String doctorFullName,
            String doctorImage,
            String doctorPosition,

            LocalDateTime dateTime,
            Status status
    ) { }
