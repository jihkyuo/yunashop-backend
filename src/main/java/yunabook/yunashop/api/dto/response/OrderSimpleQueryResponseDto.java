package yunabook.yunashop.api.dto.response;

import java.time.LocalDateTime;

import lombok.Data;
import yunabook.yunashop.domain.Address;
import yunabook.yunashop.domain.OrderStatus;

@Data
public class OrderSimpleQueryResponseDto {
  private Long orderId;
  private String name;
  private LocalDateTime orderDate;
  private OrderStatus orderStatus;
  private Address address;

  public OrderSimpleQueryResponseDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus,
      Address address) {
    this.orderId = orderId;
    this.name = name;
    this.orderStatus = orderStatus;
    this.address = address;
    this.orderDate = orderDate;
  }
}
