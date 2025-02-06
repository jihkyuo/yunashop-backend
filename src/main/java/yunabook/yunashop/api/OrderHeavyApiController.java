package yunabook.yunashop.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import yunabook.yunashop.domain.Address;
import yunabook.yunashop.domain.Order;
import yunabook.yunashop.domain.OrderItem;
import yunabook.yunashop.domain.OrderStatus;
import yunabook.yunashop.repository.OrderRepository;
import yunabook.yunashop.repository.OrderSearch;
import yunabook.yunashop.repository.order.heavyquery.OrderHeavyQueryRepository;
import yunabook.yunashop.repository.order.heavyquery.OrderHeavyQueryResponseDto;

@RestController
@RequiredArgsConstructor
public class OrderHeavyApiController {

  private final OrderRepository orderRepository;
  private final OrderHeavyQueryRepository orderHeavyQueryRepository;

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

  @GetMapping("/api/v3/orders")
  public List<OrderDto> ordersV3() {
    List<Order> all = orderRepository.findAllWithItem();
    return all.stream().map(OrderDto::new).collect(Collectors.toList());
  }

  @GetMapping("/api/v4/orders")
  public List<OrderHeavyQueryResponseDto> ordersV4() {
    return orderHeavyQueryRepository.findOrderQueryDtos();
  }

  @GetMapping("/api/v5/orders")
  public List<OrderHeavyQueryResponseDto> ordersV5() {
    return orderHeavyQueryRepository.findAllByDto_optimization();
  }

  /**
   * 컬렉션 페이징 최적화
   * 1. 우선 ToOne 관계 모두 fetch join (Member, Delivery)
   * 2. 컬렉션은 지연 로딩으로 조회
   * ! 3. (핵심) 지연 로딩 성능 향상을 위해 hibernate.default_batch_fetch_size, @BatchSize 사용
   * => 자동으로 in 쿼리 생성
   */
  @GetMapping("/api/v3.1/orders")
  public List<OrderDto> ordersV3_page(
      @RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "100") int limit) {
    List<Order> all = orderRepository.findAllWithMemberDelivery(offset, limit);
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
