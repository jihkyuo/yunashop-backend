package yunacare.yunahospital.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Doctor {

  @Id
  @GeneratedValue
  @Column(name = "doctor_id")
  private Long id;

  private String name;

  private String phone;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "specialty_id")
  private Specialty specialty;

  @OneToMany(mappedBy = "doctor")
  private List<Appointment> appointments = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hospital_id")
  private Hospital hospital;

  // ===== 연관관계 편의 메서드 =====
  public void addHospital(Hospital hospital) {
    this.hospital = hospital;
    hospital.getDoctors().add(this);
  }

  public void addSpecialty(Specialty specialty) {
    this.specialty = specialty;
    specialty.getDoctors().add(this);
  }

  // ===== 생성 메서드 =====
  public static Doctor createDoctor(String name, String phone, Specialty specialty, Hospital hospital) {
    Doctor doctor = new Doctor();
    doctor.setName(name);
    doctor.setPhone(phone);
    doctor.addSpecialty(specialty);
    doctor.addHospital(hospital);
    return doctor;
  }
  
  
}
