package yunabook.yunashop.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import yunabook.yunashop.domain.Member;

@Repository // 스프링 빈에 등록된다.
@RequiredArgsConstructor // final이 붙은 필드를 모아서 생성자를 자동으로 생성해준다.
public class MemberRepository {

  // @PersistenceContext // JPA에서 EntityManager를 주입해준다.
  // final이 붙은 필드는 @RequiredArgsConstructor 어노테이션을 사용하면 생성자를 자동으로 생성해준다.
  // => 따라서 생성자를 작성하지 않아도 된다.
  // => 따라서 EntityManager를 주입해준다.
  // => service와 일관성 있게 @RequiredArgsConstructor 어노테이션을 사용할 수 있음
  private final EntityManager em;

  public void save(Member member) {
    em.persist(member);
  }

  public Member findOne(Long id) {
    return em.find(Member.class, id);
  }

  public List<Member> findAll() {
    return em.createQuery("select m from Member m", Member.class)
        .getResultList();
  }

  public List<Member> findByName(String name) {
    return em.createQuery("select m from Member m where m.name = :name", Member.class)
        .setParameter("name", name)
        .getResultList();
  }
}
