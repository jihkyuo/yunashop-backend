package yunabook.yunashop.api.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import yunabook.yunashop.domain.Address;
import yunabook.yunashop.domain.Order;
import yunabook.yunashop.domain.OrderStatus;

@Getter
public class OrderResponseDto {
  private Long id;
  private String memberName;
  private OrderStatus orderStatus;
  private Address address;
  private List<OrderItemResponseDto> orderItems;
  private LocalDateTime orderDate;

  public OrderResponseDto(Order order) {
    this.id = order.getId();
    this.memberName = order.getMember().getName();
    this.orderStatus = order.getStatus();
    this.address = order.getDelivery().getAddress();
    this.orderItems = order.getOrderItems().stream()
        .map(OrderItemResponseDto::new)
        .collect(Collectors.toList());
    this.orderDate = order.getOrderDate();
  }
}
