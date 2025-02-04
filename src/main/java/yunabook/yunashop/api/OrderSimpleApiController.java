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
    /**
     * ! 이를 해결하기 위해 연관관계인 엔티티에서 order 필드에 @JsonIgnore 어노테이션을 사용하여 무시함
     * 하지만 조회 시 Type definition error 500 에러 발생
     * why?
     * Lazy 로딩은 db에서 가져올 때 Order만 가져오고 연관 엔티티는 가져오지 않음
     * 하이버네이트는 연관 엔티티를 조회할 때 프록시 객체를 생성해서 넣어놓는다. -> 이게 바로 byteBuddy 라는 라이브러리 이다.
     * 생성된 프록시 객체에 값이 채워지는 시점은 해당 객체가 db에서 실제 데이터를 가져오는 시점이다.
     * Type definition error 500 에러는 Jackson 라이브러리에서 객체를 뽑으려고 할 때 //! 프록시 객체를 만나서 발생하는 에러이다.
     */

    List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
    return all;
  }

}
