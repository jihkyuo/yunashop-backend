package yunabook.yunashop.repository.order.heavyquery;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import yunabook.yunashop.domain.Address;
import yunabook.yunashop.domain.OrderStatus;

@Data
public class OrderHeavyQueryResponseDto {
  private Long orderId;
  private String memberName;
  private OrderStatus orderStatus;
  private Address address;
  private List<OrderItemQueryDto> orderItems;
  private LocalDateTime orderDate;

  public OrderHeavyQueryResponseDto(
      Long orderId,
      String memberName,
      OrderStatus orderStatus,
      Address address,
      LocalDateTime orderDate) {
    this.orderId = orderId;
    this.memberName = memberName;
    this.orderStatus = orderStatus;
    this.address = address;
    this.orderDate = orderDate;
  }
}
