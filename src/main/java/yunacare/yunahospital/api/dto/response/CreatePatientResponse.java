package yunacare.yunahospital.api.dto.response;

import lombok.Data;

@Data
public class CreatePatientResponse {
  private Long id;

  public CreatePatientResponse(Long id) {
    this.id = id;
  }
}
