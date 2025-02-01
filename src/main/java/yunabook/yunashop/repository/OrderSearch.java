package yunabook.yunashop.repository;

import lombok.Getter;
import lombok.Setter;
import yunabook.yunashop.domain.OrderStatus;

@Getter
@Setter
public class OrderSearch {

  private String memberName;
  private OrderStatus orderStatus;

}
