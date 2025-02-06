package yunabook.yunashop.repository.order.heavyquery;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderHeavyQueryRepository {
  private final EntityManager em;

  public List<OrderHeavyQueryResponseDto> findOrderQueryDtos() {
    List<OrderHeavyQueryResponseDto> result = findOrders();
    result.forEach(o -> {
      List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
      o.setOrderItems(orderItems);
    });
    return result;
  }

  private List<OrderItemQueryDto> findOrderItems(Long orderId) {
    return em.createQuery(
        "select new yunabook.yunashop.repository.order.heavyquery.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) from OrderItem oi"
            +
            " join oi.item i" +
            " where oi.order.id = :orderId",
        OrderItemQueryDto.class)
        .setParameter("orderId", orderId)
        .getResultList();
  }

  private List<OrderHeavyQueryResponseDto> findOrders() {
    return em.createQuery(
        "select new yunabook.yunashop.repository.order.heavyquery.OrderHeavyQueryResponseDto(o.id, m.name, o.status, d.address, o.orderDate) from Order o"
            +
            " join o.member m" +
            " join o.delivery d",
        OrderHeavyQueryResponseDto.class)
        .getResultList();
  }
}
