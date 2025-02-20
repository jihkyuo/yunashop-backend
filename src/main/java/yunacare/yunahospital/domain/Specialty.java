package yunacare.yunahospital.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Specialty {

  @Id
  @GeneratedValue
  @Column(name = "specialty_id")
  private Long id;

  private String name;

  @OneToMany(mappedBy = "specialty")
  private List<Doctor> doctors = new ArrayList<>();

  // ===== 생성 메서드 =====
  public static Specialty createSpecialty(String name) {
    Specialty specialty = new Specialty();
    specialty.setName(name);
    return specialty;
  }
}
