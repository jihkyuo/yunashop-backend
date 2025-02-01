package yunabook.yunashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
// 값 타입은 변경 불가능하게 설계해야 한다. => 그래서 @Setter X
public class Address {

  private String city;
  private String street;
  private String zipcode;

  // JPA 스펙상 엔티티나 임베디드 타입은 기본 생성자를 만들어야 한다.
  // why? JPA가 엔티티를 생성할 때 기본 생성자를 호출해서 엔티티를 생성하기 때문이다.
  protected Address() {
  }

  // 생성자를 만들어주면 JPA가 기본 생성자를 만들지 않는다.
  public Address(String city, String street, String zipcode) {
    this.city = city;
    this.street = street;
    this.zipcode = zipcode;
  }
}
