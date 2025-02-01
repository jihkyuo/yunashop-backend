package yunabook.yunashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import yunabook.yunashop.domain.Delivery;
import yunabook.yunashop.domain.Member;
import yunabook.yunashop.domain.Order;
import yunabook.yunashop.domain.OrderItem;
import yunabook.yunashop.domain.item.Item;
import yunabook.yunashop.repository.ItemRepository;
import yunabook.yunashop.repository.MemberRepository;
import yunabook.yunashop.repository.OrderRepository;
import yunabook.yunashop.repository.OrderSearch;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final MemberRepository memberRepository;
  private final ItemRepository itemRepository;

  /**
   * 주문
   * 
   */
  @Transactional
  public Long order(Long memberId, Long itemId, int count) {
    // 엔티티 조회
    Member member = memberRepository.findOne(memberId);
    Item item = itemRepository.findOne(itemId);

    // 배송정보 생성
    Delivery delivery = new Delivery();
    delivery.setAddress(member.getAddress());

    // 주문상품 생성
    OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

    // 주문 생성
    Order order = Order.createOrder(member, delivery, orderItem);

    // 주문 저장
    // CascadeType.ALL 옵션으로 인해 orderItem과 delivery도 함께 저장됨 => OrderItem과 Delivery를
    // 따로 가져올 필요가 없음
    orderRepository.save(order);

    return order.getId();
  }

  // 취소
  @Transactional
  public void cancelOrder(Long orderId) {
    // 주문 엔티티 조회
    Order order = orderRepository.findOne(orderId);
    // 주문 취소
    order.cancel();
  }

  // 검색
  public List<Order> findOrders(OrderSearch orderSearch) {
    return orderRepository.findAllByCriteria(orderSearch);
  }
}
