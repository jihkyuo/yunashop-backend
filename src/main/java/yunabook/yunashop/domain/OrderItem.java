package yunabook.yunashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yunabook.yunashop.domain.item.Item;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

  @Id
  @GeneratedValue
  @Column(name = "order_item_id")
  private Long id;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;

  private int orderPrice;

  private int count; // 주문 수량

  // protected 생성자를 통해 엔티티 생성을 제한함
  // protected OrderItem() {}

  // ===== 생성 메서드 =====
  public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
    OrderItem orderItem = new OrderItem();
    orderItem.setItem(item);
    orderItem.setOrderPrice(orderPrice);
    orderItem.setCount(count);
    
    item.removeStock(count);
    return orderItem;
  }

  // ===== 비즈니스 로직 =====
  public void cancel() {
    getItem().addStock(count); // 재고 수량 원복 => 주문 했던 수량만큼 재고에 다시 넣어줌
  }

  public int getTotalPrice() {
    return getOrderPrice() * getCount();
  }
}
