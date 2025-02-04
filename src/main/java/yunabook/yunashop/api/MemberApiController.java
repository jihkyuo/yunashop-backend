package yunabook.yunashop.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import yunabook.yunashop.api.dto.request.CreateMemberRequest;
import yunabook.yunashop.api.dto.request.UpdateMemberRequest;
import yunabook.yunashop.api.dto.response.CreateMemberResponse;
import yunabook.yunashop.api.dto.response.MemberResponseDto;
import yunabook.yunashop.api.dto.response.ResultResponse;
import yunabook.yunashop.api.dto.response.UpdateMemberResponse;
import yunabook.yunashop.domain.Address;
import yunabook.yunashop.domain.Member;
import yunabook.yunashop.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

  private final MemberService memberService;

  // 회원 조회
  // DTO를 사용하지 않고 엔티티를 그대로 반환하면 orders와 양방향 관계로 순환 참조가 발생하여 결과 값이 무한히 증식함
  @GetMapping("/members")
  public ResultResponse<List<MemberResponseDto>> findMembers() {
    List<MemberResponseDto> collect = memberService.findMembers().stream()
        .map(MemberResponseDto::new)
        .collect(Collectors.toList());
    return new ResultResponse<>(collect);
  }

  /**
   * 회원 가입
   * 
   * @param CreateMemberRequest
   * @return CreateMemberResponse
   */
  @PostMapping("/members/join")
  public CreateMemberResponse createMember(@RequestBody @Valid CreateMemberRequest request) {
    Address address = new Address(request.getCity(), request.getStreet(), request.getZipcode());
    Member member = new Member();
    member.setName(request.getName());
    member.setAddress(address);
    Long id = memberService.join(member);
    return new CreateMemberResponse(id);
  }

  /**
   * 회원 수정
   * 
   * @param UpdateMemberRequest
   * @return UpdateMemberResponse
   */
  @PutMapping("/members/{id}")
  public UpdateMemberResponse updateMember(@PathVariable Long id, @RequestBody @Valid UpdateMemberRequest request) {
    memberService.update(id, request.getName(), request.getCity(), request.getStreet(), request.getZipcode());
    Member findMember = memberService.findOne(id);
    return new UpdateMemberResponse(id, findMember.getName());
  }

  @GetMapping("/members/{memberId}")
  public Member findOne(@PathVariable Long memberId) {
    return memberService.findOne(memberId);
  }
}
