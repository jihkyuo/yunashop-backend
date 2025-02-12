package yunacare.yunahospital.api.dto.request;

import lombok.Data;

@Data
public class PatientSearchRequest {
  private String name;
  private String phone;
  private String city;
}
