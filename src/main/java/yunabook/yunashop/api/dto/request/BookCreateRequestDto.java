package yunabook.yunashop.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCreateRequestDto {

  @NotEmpty(message = "상품명은 필수 입력 값입니다.")
  private String name;

  @NotNull(message = "가격은 필수 입력 값입니다.")
  private Integer price;

  @NotNull(message = "재고는 필수 입력 값입니다.")
  private Integer stockQuantity;

  private String author;

  private String isbn;
}
