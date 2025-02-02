package yunabook.yunashop.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateMemberRequest {

  @NotEmpty(message = "이름은 필수 입력 값입니다.")
  private String name;
  private String city;
  private String street;
  private String zipcode;

}