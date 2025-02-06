package yunabook.yunashop.repository.order.heavyquery;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

  public List<OrderHeavyQueryResponseDto> findAllByDto_optimization() {
    List<OrderHeavyQueryResponseDto> result = findOrders();
    Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(toOrderIds(result));

    result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));
    return result;
  }

  private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
    List<OrderItemQueryDto> orderItems = em.createQuery(
        "select new yunabook.yunashop.repository.order.heavyquery.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) from OrderItem oi"
            +
            " join oi.item i" +
            " where oi.order.id in :orderIds",
        OrderItemQueryDto.class)
        .setParameter("orderIds", orderIds)
        .getResultList();

    Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
        .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
    return orderItemMap;
  }

  private List<Long> toOrderIds(List<OrderHeavyQueryResponseDto> result) {
    return result.stream().map(o -> o.getOrderId()).collect(Collectors.toList());
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
