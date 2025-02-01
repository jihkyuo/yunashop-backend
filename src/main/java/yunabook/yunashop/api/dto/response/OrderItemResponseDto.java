package yunabook.yunashop.api.dto.response;

import lombok.Getter;
import yunabook.yunashop.domain.OrderItem;

@Getter
public class OrderItemResponseDto {
  private Long id;
  private String itemName;
  private int orderPrice;
  private int count;
  
  public OrderItemResponseDto(OrderItem orderItem) {
    this.id = orderItem.getId();
    this.itemName = orderItem.getItem().getName();
    this.orderPrice = orderItem.getOrderPrice();
    this.count = orderItem.getCount();
  }
}
