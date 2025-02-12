package yunacare.yunahospital.api.dto.response;

import lombok.Data;

@Data
public class ResultResponse<T> {
  private T result;

  public ResultResponse(T result) {
    this.result = result;
  }
}