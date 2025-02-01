package yunabook.yunashop.api.dto.response;

import lombok.Getter;
import yunabook.yunashop.domain.Address;
import yunabook.yunashop.domain.Member;

@Getter
public class MemberResponseDto {
  private Long id;
  private String name;
  private Address address;

  public MemberResponseDto(Member member) {
    this.id = member.getId();
    this.name = member.getName();
    this.address = member.getAddress();
  }
}
