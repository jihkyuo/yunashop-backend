package yunabook.yunashop.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import yunabook.yunashop.api.dto.response.OrderSimpleQueryResponseDto;
import yunabook.yunashop.domain.Member;
import yunabook.yunashop.domain.Order;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

  private final EntityManager em;

  public void save(Order order) {
    em.persist(order);
  }

  public Order findOne(Long id) {
    return em.find(Order.class, id);
  }

  // 검색 메서드
  public List<Order> findAll(OrderSearch orderSearch) {
    return em.createQuery("select o from Order o join o.member m", Order.class)
        .setMaxResults(1000)
        .getResultList();
  }

  public List<Order> findAllByCriteria(OrderSearch orderSearch) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Order> cq = cb.createQuery(Order.class);
    Root<Order> o = cq.from(Order.class);
    Join<Order, Member> m = o.join("member", JoinType.INNER);

    List<Predicate> criteria = new ArrayList<>();

    // 주문 상태 검색
    if (orderSearch.getOrderStatus() != null) {
      Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
      criteria.add(status);
    }

    // 회원 이름 검색
    if (orderSearch.getMemberName() != null) {
      Predicate name = cb.like(m.get("name"), "%" + orderSearch.getMemberName() + "%");
      criteria.add(name);
    }

    cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
    TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
    return query.getResultList();
  }

  /**
   * ! 매우중요: fetch join 사용
   * sql 입장에서는 1번 조회하면서 select 절에 연관 관계인 테이블을 조인하여 한번에 가져옴
   * 
   * 장점: v4보다 재사용성이 높음
   * 단점: 엔티티를 다 가져옴
   */
  public List<Order> findAllWithMemberDelivery() {
    return em.createQuery("select o from Order o" +
        " join fetch o.member m" +
        " join fetch o.delivery d", Order.class)
        .getResultList();
  }

  /**
   * 장점: dto로 원하는 프로퍼티만 골라서 쿼리하기 때문에
   * v3보다 쿼리가 짧아지고, 성능이 좋아짐
   * 
   * 단점
   * - v3보다 재사용성이 떨어짐
   * - repository는 엔티티의 그래프를 탐색하는 것으로 해야 의존성 분리가 있는 것인데,
   * dto로 조회함으로써 api 스펙이 그대로 들어와 화면에 의존적으로 되면서 화면이 변경될 때마다 repository도 변경되어야 함
   * - v3과 비교해서 필드 몇개 줄이는 시도가 네크워크 성능적으로 크게 차이나지 않음
   */
  public List<OrderSimpleQueryResponseDto> findOrdersDto() {
    return em.createQuery(
        "select new yunabook.yunashop.api.dto.response.OrderSimpleQueryResponseDto(o.id, m.name, o.orderDate, o.status, d.address)"
            +
            " from Order o" +
            " join o.member m" +
            " join o.delivery d",
        OrderSimpleQueryResponseDto.class)
        .getResultList();
  }

}
