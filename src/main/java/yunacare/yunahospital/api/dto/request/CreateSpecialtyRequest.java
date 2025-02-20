package yunacare.yunahospital.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateSpecialtyRequest {

  @NotEmpty(message = "이름은 필수 입력 값입니다.")
  private String name;
}
