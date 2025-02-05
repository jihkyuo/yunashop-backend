package yunabook.yunashop.repository.order.simplequery;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

  private final EntityManager em;

  /**
   * api 스펙에 의존적인 repository를 별도 패키지로 분리함
   */
  public List<OrderSimpleQueryResponseDto> findOrdersDto() {
    return em.createQuery(
        "select new yunabook.yunashop.repository.order.simplequery.OrderSimpleQueryResponseDto(o.id, m.name, o.orderDate, o.status, d.address)"
            +
            " from Order o" +
            " join o.member m" +
            " join o.delivery d",
        OrderSimpleQueryResponseDto.class)
        .getResultList();
  }
}
