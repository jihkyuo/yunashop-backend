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

  public List<Order> findAllWithItem() {
    /**
     * distinct는 oneToMany 조인 시 중복된 결과를 제거하는 역할
     * 중복된 결과를 제거하는 이유는 조인 시 중복된 결과가 나오기 때문
     * 예를 들어, 주문 테이블과 회원 테이블을 조인하면 주문 테이블의 중복된 결과가 나오기 때문에 중복된 결과를 제거
     * */ 

    /**
     * v2와 비교하여 fetch join 덕분에 쿼리가 1번으로 대폭 줄어듦
     * 
     * ! 치명적 단점: oneToMany는 페이징 불가능
     * order 2, orderItem 4 일때 일대다 조인 시 원하는 결과는 2개이지만, db에서는 4개의 row가 조회됨
     * distinct 추가 시 애플리케이션 레벨에서 db에서 가져온 4개 중 중복된 결과를 제거하여 2개의 row만 가져옴
     * 즉, 페이징을 한다면 4개의 중복된 row를 애플리케이션에서 다 퍼올린 후 중복을 제거해서 메모리에서 페이징을 처리하기 때문에
     * 조회 수가 많아질 수록 메모리 부하가 엄청 커짐
     * */  
    return em.createQuery("select distinct o from Order o" +
        " join fetch o.member m" +
        " join fetch o.delivery d" +
        " join fetch o.orderItems oi" +
        " join fetch oi.item i", Order.class)
        .setFirstResult(1)
        .setMaxResults(100)
        .getResultList();
  }
}
