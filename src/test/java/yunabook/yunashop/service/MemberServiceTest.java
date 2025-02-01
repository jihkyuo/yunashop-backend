package yunabook.yunashop.service;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import yunabook.yunashop.domain.Member;
import yunabook.yunashop.repository.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

  @Autowired
  private MemberService memberService;

  @Autowired
  private MemberRepository memberRepository;

  @Test
  // @Rollback(false) // 테스트 코드 실행 후 롤백을 하지 않는다.
  // @Transactional 어노테이션을 사용하면 테스트 코드 실행 후 기본적으로 롤백을 해준다.
  // 하지만 테스트 코드에서 롤백을 하지 않으려면 @Rollback(false) 어노테이션을 사용해준다.
  public void 회원가입() throws Exception {
    // given
    Member member = new Member();
    member.setName("yuna");

    // when
    Long savedId = memberService.join(member);

    // then
    Member findMember = memberRepository.findOne(savedId);
    assertEquals(member.getName(), findMember.getName());

  }

  @Test(expected = IllegalStateException.class)
  public void 중복_회원_예외() throws Exception {
    // given
    Member member1 = new Member();
    member1.setName("yuna");

    Member member2 = new Member();
    member2.setName("yuna");

    // when
    memberService.join(member1);
    memberService.join(member2);

    // then
    fail("예외가 발생해야 한다.");
  }
}
