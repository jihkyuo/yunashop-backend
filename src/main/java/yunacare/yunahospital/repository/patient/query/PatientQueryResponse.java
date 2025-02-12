package yunacare.yunahospital.repository.patient.query;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import yunacare.yunahospital.domain.Address;

@Data
@EqualsAndHashCode(of = "patientId")
public class PatientQueryResponse {
  private Long patientId;
  private String name;
  private String phone;
  private Address address;
  private List<AppointmentQueryResponse> appointments;

  public PatientQueryResponse(
      Long patientId,
      String name,
      String phone,
      Address address) {
    this.patientId = patientId;
    this.name = name;
    this.phone = phone;
    this.address = address == null ? Address.empty() : address;
    this.appointments = new ArrayList<>();
  }

  public PatientQueryResponse(
      Long patientId,
      String name,
      String phone,
      Address address,
      List<AppointmentQueryResponse> appointments) {
    this.patientId = patientId;
    this.name = name;
    this.phone = phone;
    this.address = address == null ? Address.empty() : address;
    this.appointments = appointments == null ? new ArrayList<>() : appointments;
  }
}
