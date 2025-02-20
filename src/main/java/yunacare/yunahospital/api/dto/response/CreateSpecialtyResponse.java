package yunacare.yunahospital.api.dto.response;

import lombok.Data;

@Data
public class CreateSpecialtyResponse {
  private Long specialtyId;

  public CreateSpecialtyResponse(Long specialtyId) {
    this.specialtyId = specialtyId;
  }
}