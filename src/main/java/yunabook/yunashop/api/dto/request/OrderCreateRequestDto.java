package yunabook.yunashop.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateRequestDto {
  @NotNull(message = "회원 아이디는 필수 입력 값입니다.")
  private Long memberId;

  @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
  private Long itemId;

  @Min(value = 1, message = "최소 1개 이상 주문해야 합니다.")
  private int count;
}