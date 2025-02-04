package yunabook.yunashop.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import yunabook.yunashop.domain.Order;
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
    List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
    return all;
  }

}
