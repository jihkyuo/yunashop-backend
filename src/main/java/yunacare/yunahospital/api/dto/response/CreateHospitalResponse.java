package yunacare.yunahospital.api.dto.response;

import lombok.Data;

@Data
public class CreateHospitalResponse {
  private Long id;

  public CreateHospitalResponse(Long id) {
    this.id = id;
  }
}
