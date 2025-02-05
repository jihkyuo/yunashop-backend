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
import yunabook.yunashop.domain.OrderStatus;
import yunabook.yunashop.repository.OrderRepository;
import yunabook.yunashop.repository.OrderSearch;

/**
 * xToOne 관계 최적화 실습
 * Order 조회
 * Order -> Member 연관 관계 매핑(ManyToOne)
 * Order -> Delivery 연관 관계 매핑(OneToOne)
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
  private final OrderRepository orderRepository;

  @GetMapping("/api/v1/simple-orders")
  public List<Order> ordersV1() {
    // ! 이 상태로 조회를 하면 양방향 관계인 Order와 Member가 서로 호출하여 무한 루프에 빠짐
    /**
     * ! 이를 해결하기 위해 연관관계인 엔티티에서 order 필드에 @JsonIgnore 어노테이션을 사용하여 무시함
     * 하지만 조회 시 Type definition error 500 에러 발생
     * why?
     * Lazy 로딩은 db에서 가져올 때 Order만 가져오고 연관 엔티티는 가져오지 않음
     * 하이버네이트는 연관 엔티티를 조회할 때 프록시 객체를 생성해서 넣어놓는다. -> 이게 바로 byteBuddy 라는 라이브러리 이다.
     * 생성된 프록시 객체에 값이 채워지는 시점은 해당 객체가 db에서 실제 데이터를 가져오는 시점이다.
     * Type definition error 500 에러는 Jackson 라이브러리에서 객체를 뽑으려고 할 때 //! 프록시 객체를 만나서
     * 발생하는 에러이다.
     */

    List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());

    // 강제로 지연 로딩 대상을 조회하여 프록시 객체를 초기화 시킴
    for (Order order : all) {
      order.getMember().getName();
      order.getDelivery().getAddress();
    }
    return all;
  }

  @GetMapping("/api/v2/simple-orders")
  public List<OrderSimpleDto> ordersV2() {
    //! 이 api도 문제가 있음 => N+1 문제가 발생함 (성능이 떨어짐)
    /**
     * 주문 수가 2개인 경우 각 주문별 루프를 돌 때 Member와 Delivery를 조회하는 쿼리가 2번 실행됨
     * why?
     * 주문 조회 시 연관 관계인 Member와 Delivery는 //! 지연 로딩으로 조회되지 않음
     * 주문 조회 후 각 주문별로 연관 관계인 Member와 Delivery를 조회하기 위해 추가적인 쿼리가 실행됨
     * 결과적으로 주문 수가 2개인 경우 총 5번의 쿼리가 실행됨
     * */
    List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
    List<OrderSimpleDto> collect = orders.stream()
        .map(OrderSimpleDto::new)
        .collect(Collectors.toList());

    return collect;
  }

  @Data
  static class OrderSimpleDto {
    private Long orderId;
    private String memberName;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleDto(Order order) {
      orderId = order.getId();
      memberName = order.getMember().getName();
      orderDate = order.getOrderDate();
      orderStatus = order.getStatus();
      address = order.getDelivery().getAddress();
    }
  }
}
