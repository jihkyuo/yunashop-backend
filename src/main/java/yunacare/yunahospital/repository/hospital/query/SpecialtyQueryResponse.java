package yunacare.yunahospital.repository.hospital.query;

import lombok.Data;

@Data
public class SpecialtyQueryResponse {
  private Long hospitalId;
  private Long specialtyId;
  private String name;

  public SpecialtyQueryResponse(Long hospitalId, Long specialtyId, String name) {
    this.hospitalId = hospitalId;
    this.specialtyId = specialtyId;
    this.name = name;
  }

  public SpecialtyQueryResponse(Long specialtyId, String name) {
    this.specialtyId = specialtyId;
    this.name = name;
  }
}
