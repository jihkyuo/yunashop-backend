package yunacare.yunahospital.repository.hospital.query;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "hospitalId")
public class HospitalQueryResponse {
  private Long hospitalId;
  private String name;
  private List<SpecialtyQueryResponse> specialties;

  public HospitalQueryResponse(Long hospitalId, String name) {
    this.hospitalId = hospitalId;
    this.name = name;
    this.specialties = new ArrayList<>();
  }

  public HospitalQueryResponse(
      Long hospitalId,
      String name,
      List<SpecialtyQueryResponse> specialties) {
    this.hospitalId = hospitalId;
    this.name = name;
    this.specialties = specialties == null ? new ArrayList<>() : specialties;
  }
}
