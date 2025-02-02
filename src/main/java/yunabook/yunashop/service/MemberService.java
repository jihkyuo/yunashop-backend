package yunabook.yunashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import yunabook.yunashop.domain.Address;
import yunabook.yunashop.domain.Member;
import yunabook.yunashop.repository.MemberRepository;

@Service
@Transactional // JPA안에서 데이터를 변경하는 메서드에는 필수적으로 Transactional 어노테이션을 붙여줘야 한다
// class 레벨에 @Transactional 어노테이션을 붙여주면 모든 메서드에 동일하게 적용되며,
// 메서드 레벨에 @Transactional 어노테이션을 붙여주면 해당 메서드에만 설정한 내용이 우선으로 적용된다.

@RequiredArgsConstructor // final이 붙은 필드를 모아서 생성자를 자동으로 생성해준다.
public class MemberService {

  // @Autowired // 스프링 빈에 등록된 객체를 주입해준다.(DI)
  // 변경 불가능한 객체는 final로 선언해준다.
  private final MemberRepository memberRepository;

  // @Autowired
  // 생성자 인젝션 방식이 요즘 추세임 => 애플리케이션 실행 시점에 주입됨
  // 그래서 실행 이후 변경이 불가능
  // 또한 테스트 코드 작성 시 편리함
  // 최신 스프링에서는 @Autowired 어노테이션을 선언하지 않아도 자동으로 주입된다.
  // @RequiredArgsConstructor 어노테이션을 사용하면 final이 붙은 필드를 모아서 생성자를 자동으로 생성해준다. =>
  // 따라서 생성자 코드를 작성하지 않아도 된다.
  // public MemberService(MemberRepository memberRepository) {
  // this.memberRepository = memberRepository;
  // }

  /**
   * 회원 가입
   */
  @Transactional // 데이터를 변경하는 메서드에는 readOnly = true를 사용하면 안된다. => 변경이 안됨
  public Long join(Member member) {
    validateDuplicateMember(member.getName()); // 중복 회원 검증
    memberRepository.save(member);

    // 영속성 컨텍스트가 db에 persist 하는 시점에 id가 생성되기 때문에 id가 있는 것을 보장해준다.
    return member.getId();
  }

  /**
   * 중복 회원 검증
   */
  private void validateDuplicateMember(String name) {
    List<Member> findMembers = memberRepository.findByName(name);
    if (!findMembers.isEmpty()) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
  }

  /**
   * 회원 전체 조회
   */
  @Transactional(readOnly = true) // 읽기 전용으로 설정하여 성능을 최적화 할 수 있다.
  public List<Member> findMembers() {
    return memberRepository.findAll();
  }

  /**
   * 회원 단건 조회
   */
  @Transactional(readOnly = true)
  public Member findOne(Long memberId) {
    return memberRepository.findOne(memberId);
  }

  /**
   * 회원 수정
   */
  @Transactional
  public void update(Long id, String name, String city, String street, String zipcode) {
    Member member = memberRepository.findOne(id);

    if (name != null) {
      validateDuplicateMember(name);
      member.setName(name);
    }
    if (city != null || street != null || zipcode != null) {
      Address currentAddress = member.getAddress();
      String newCity = city != null ? city : currentAddress.getCity();
      String newStreet = street != null ? street : currentAddress.getStreet();
      String newZipcode = zipcode != null ? zipcode : currentAddress.getZipcode();
      member.setAddress(new Address(newCity, newStreet, newZipcode));
    }
  }
}
