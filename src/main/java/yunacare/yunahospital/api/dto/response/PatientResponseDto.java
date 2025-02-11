package yunacare.yunahospital.api.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.Getter;
import yunacare.yunahospital.domain.Address;
import yunacare.yunahospital.domain.Appointment;
import yunacare.yunahospital.domain.AppointmentStatus;
import yunacare.yunahospital.domain.Patient;

@Getter
public class PatientResponseDto {
  private Long id;
  private String name;
  private String phone;
  private Address address;
  private List<AppointmentResponseDto> appointments;

  public PatientResponseDto(Patient patient) {
    this.id = patient.getId();
    this.name = patient.getName();
    this.phone = patient.getPhone();
    this.address = patient.getAddress();
    this.appointments = patient.getAppointments().stream()
        .map(AppointmentResponseDto::new)
        .collect(Collectors.toList());
  }

  @Data
  static class AppointmentResponseDto {
    private Long id;
    private LocalDateTime appointmentDate;
    private AppointmentStatus status;

    public AppointmentResponseDto(Appointment appointment) {
      this.id = appointment.getId();
      this.appointmentDate = appointment.getAppointmentDate();
      this.status = appointment.getStatus();
    }
  }
}
