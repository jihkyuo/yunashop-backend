package yunacare.yunahospital.repository.patient.query;

import java.time.LocalDateTime;

import lombok.Data;
import yunacare.yunahospital.domain.AppointmentStatus;

@Data
public class AppointmentQueryResponse {
  private Long patientId;
  private Long appointmentId;
  private LocalDateTime appointmentDate;
  private AppointmentStatus status;

  public AppointmentQueryResponse(
      Long patientId,
      Long appointmentId,
      LocalDateTime appointmentDate,
      AppointmentStatus status) {
    this.patientId = patientId;
    this.appointmentId = appointmentId;
    this.appointmentDate = appointmentDate;
    this.status = status;
  }
}
