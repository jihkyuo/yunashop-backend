package yunacare.yunahospital.api.dto.response;

import lombok.Getter;

@Getter
public class SpecialtyResponse {
  private Long specialtyId;
  private String name;

  public SpecialtyResponse(Long specialtyId, String name) {
    this.specialtyId = specialtyId;
    this.name = name;
  }
}
