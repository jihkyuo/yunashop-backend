package yunabook.yunashop.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import yunabook.yunashop.domain.Address;
import yunabook.yunashop.domain.Order;
import yunabook.yunashop.domain.OrderItem;
import yunabook.yunashop.domain.OrderStatus;
import yunabook.yunashop.repository.OrderRepository;
import yunabook.yunashop.repository.OrderSearch;

@RestController
@RequiredArgsConstructor
public class OrderHeavyApiController {

  private final OrderRepository orderRepository;

  @GetMapping("/api/v1/orders")
  public List<Order> ordersV1() {
    List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
    for (Order order : all) {
      order.getMember().getName();
      order.getDelivery().getAddress();
      List<OrderItem> orderItems = order.getOrderItems();
      orderItems.stream().forEach(o -> o.getItem().getName());
    }
    return all;
  }

  @GetMapping("/api/v2/orders")
  public List<OrderDto> ordersV2() {
    List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
    return all.stream().map(OrderDto::new).collect(Collectors.toList());
  }

  @Data
  static class OrderDto {
    private Long orderId;
    private String memberName;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
      this.orderId = order.getId();
      this.memberName = order.getMember().getName();
      this.orderDate = order.getOrderDate();
      this.orderStatus = order.getStatus();
      this.address = order.getDelivery().getAddress();
      this.orderItems = order.getOrderItems().stream()
          .map(OrderItemDto::new)
          .collect(Collectors.toList());
    }
  }

  @Data
  static class OrderItemDto {
    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemDto(OrderItem orderItem) {
      this.itemName = orderItem.getItem().getName();
      this.orderPrice = orderItem.getOrderPrice();
      this.count = orderItem.getCount();
    }
  }

}
