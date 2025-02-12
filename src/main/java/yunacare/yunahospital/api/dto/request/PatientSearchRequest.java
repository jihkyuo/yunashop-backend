package yunacare.yunahospital.api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PatientSearchRequest {
  @Min(value = 0, message = "offset은 0 이상이어야 합니다.")
  private Integer offset = 0;

  @Min(value = 1, message = "limit은 1 이상이어야 합니다.")
  @Max(value = 100, message = "limit은 100 이하여야 합니다.")
  private Integer limit = 10;

  private String name;
  private String phone;
  private String city;
}
