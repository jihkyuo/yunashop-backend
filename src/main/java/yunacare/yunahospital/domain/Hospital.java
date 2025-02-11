package yunacare.yunahospital.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
public class Hospital {

  @Id
  @GeneratedValue
  @Column(name = "hospital_id")
  private Long id;

  private String name;

  @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
  private List<Doctor> doctors = new ArrayList<>();

  // ===== 생성 메서드 =====
  public static Hospital createHospital(String name) {
    Hospital hospital = new Hospital();
    hospital.setName(name);
    return hospital;
  }
}
