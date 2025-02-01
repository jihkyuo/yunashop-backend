package yunabook.yunashop.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import yunabook.yunashop.api.dto.request.MemberCreateRequestDto;
import yunabook.yunashop.api.dto.response.MemberResponseDto;
import yunabook.yunashop.domain.Address;
import yunabook.yunashop.domain.Member;
import yunabook.yunashop.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

  private final MemberService memberService;

  @PostMapping("/members/join")
  public Long createMember(@RequestBody @Valid MemberCreateRequestDto request) {
    Address address = new Address(request.getCity(), request.getStreet(), request.getZipcode());
    Member member = new Member();
    member.setName(request.getName());
    member.setAddress(address);
    memberService.join(member);
    return member.getId();
  }

  // 회원 조회
  // DTO를 사용하지 않고 엔티티를 그대로 반환하면 orders와 양방향 관계로 순환 참조가 발생하여 결과 값이 무한히 증식함
  @GetMapping("/members")
  public List<MemberResponseDto> findMembers() {
    return memberService.findMembers().stream()
        .map(MemberResponseDto::new)
        .collect(Collectors.toList());
  }

  @GetMapping("/members/{memberId}")
  public Member findOne(@PathVariable Long memberId) {
    return memberService.findOne(memberId);
  }
}
