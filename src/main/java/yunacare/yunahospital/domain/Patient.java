package yunacare.yunahospital.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Patient {

  @Id
  @GeneratedValue
  @Column(name = "patient_id")
  private Long id;

  private String name;

  private String phone;

  @Embedded
  private Address address;

  @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
  private List<Appointment> appointments = new ArrayList<>();

}
