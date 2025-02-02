package yunabook.yunashop.api.dto.request;

import lombok.Data;

@Data
public class UpdateMemberRequest {
  private String name;
  private String city;
  private String street;
  private String zipcode;
}
