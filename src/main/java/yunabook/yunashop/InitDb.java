package yunabook.yunashop;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import yunabook.yunashop.domain.Address;
import yunabook.yunashop.domain.Delivery;
import yunabook.yunashop.domain.Member;
import yunabook.yunashop.domain.Order;
import yunabook.yunashop.domain.OrderItem;
import yunabook.yunashop.domain.item.Book;

/**
 * 총 주문 2개
 * userA
 * - JPA1 BOOK
 * - JPA2 BOOK
 * userB
 * - SPRING1 BOOK
 * - SPRING2 BOOK
 */
@Component // 스프링 빈으로 등록
@RequiredArgsConstructor
public class InitDb {

  private final InitDbService initDbService;

  @PostConstruct // 스프링 빈이 생성되고 주입을 받은 후에 스프링에서 자동으로 호출되는 메서드
  public void init() {
    initDbService.dbInit1();
    initDbService.dbInit2();
  }

  @Component
  @RequiredArgsConstructor
  @Transactional
  public static class InitDbService {
    private final EntityManager em;

    public void dbInit1() {
      Member member = createMember("userA", "서울", "섬밭로", "1111");
      em.persist(member);

      Book book1 = createBook("JPA1 BOOK", 10000, 100);
      em.persist(book1);

      Book book2 = createBook("JPA2 BOOK", 20000, 200);
      em.persist(book2);

      OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

      Delivery delivery = createDelivery(member);
      Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
      em.persist(order);
    }

    public void dbInit2() {
      Member member = createMember("userB", "부산", "해도로", "2222");
      em.persist(member);

      Book book1 = createBook("SPRING1 BOOK", 20000, 200);
      em.persist(book1);

      Book book2 = createBook("SPRING2 BOOK", 40000, 300);
      em.persist(book2);

      OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 2);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

      Delivery delivery = createDelivery(member);
      Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
      em.persist(order);
    }

    private Delivery createDelivery(Member member) {
      Delivery delivery = new Delivery();
      delivery.setAddress(member.getAddress());
      return delivery;
    }

    private Book createBook(String name, int price, int stockQuantity) {
      Book book1 = new Book();
      book1.setName(name);
      book1.setPrice(price);
      book1.setStockQuantity(stockQuantity);
      return book1;
    }

    private Member createMember(String name, String city, String street, String zipcode) {
      Member member = new Member();
      member.setName(name);
      member.setAddress(new Address(city, street, zipcode));
      return member;
    }
  }
}
