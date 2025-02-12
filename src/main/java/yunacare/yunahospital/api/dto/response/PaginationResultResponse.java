package yunacare.yunahospital.api.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginationResultResponse<T> {
  private List<T> result;
  private int page;
  private int size;
  private int total;

  public PaginationResultResponse(Page<T> page) {
    this.result = page.getContent();
    this.page = page.getNumber();
    this.size = page.getSize();
    this.total = (int) page.getTotalElements();
  }
}
