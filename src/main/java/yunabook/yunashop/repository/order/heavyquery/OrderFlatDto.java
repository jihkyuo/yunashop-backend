package yunabook.yunashop.repository.order.heavyquery;

import java.time.LocalDateTime;

import lombok.Data;
import yunabook.yunashop.domain.Address;
import yunabook.yunashop.domain.OrderStatus;

@Data
public class OrderFlatDto {

  private Long orderId;
  private String memberName;
  private LocalDateTime orderDate;
  private OrderStatus orderStatus;
  private Address address;
  private String itemName;
  private int orderPrice;
  private int count;

  public OrderFlatDto(Long orderId, String memberName, LocalDateTime orderDate, OrderStatus orderStatus,
      Address address, String itemName, int orderPrice, int count) {
    this.orderId = orderId;
    this.memberName = memberName;
    this.orderDate = orderDate;
    this.orderStatus = orderStatus;
    this.address = address;
    this.itemName = itemName;
    this.orderPrice = orderPrice;
    this.count = count;
  }

}
