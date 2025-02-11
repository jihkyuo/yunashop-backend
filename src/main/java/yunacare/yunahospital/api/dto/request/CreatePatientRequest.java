package yunacare.yunahospital.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreatePatientRequest {
  
  @NotEmpty(message = "이름은 필수 입력 값입니다.")
  private String name;

  @NotEmpty(message = "전화번호는 필수 입력 값입니다.")
  private String phone;

  private String city;
  private String street;
  private String zipcode;
}
