package yunacare.yunahospital.domain;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Appointment {

  @Id
  @GeneratedValue
  @Column(name = "appointment_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "patient_id")
  private Patient patient;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "doctor_id")
  private Doctor doctor;

  private LocalDateTime appointmentDate; // 예약 날짜

  @Enumerated(EnumType.STRING)
  private AppointmentStatus status;

  @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
  private Consultation consultation;

  // ===== 연관관계 편의 메서드 =====
  public void addPatient(Patient patient) {
    this.patient = patient;
    patient.getAppointments().add(this);
  }

  public void addDoctor(Doctor doctor) {
    this.doctor = doctor;
    doctor.getAppointments().add(this);
  }

  // ===== 생성 메서드 =====
  public static Appointment createAppointment(Patient patient, Doctor doctor) {
    Appointment appointment = new Appointment();
    appointment.addPatient(patient);
    appointment.addDoctor(doctor);
    appointment.setStatus(AppointmentStatus.PENDING);
    appointment.setAppointmentDate(LocalDateTime.now());
    return appointment;
  }

  // ===== 비즈니스 로직 =====
  /**
   * 예약 취소
   */
  public void cancel() {
    if (status == AppointmentStatus.CONFIRMED) {
      throw new IllegalStateException("진료중인 예약은 취소할 수 없습니다.");
    }
    this.setStatus(AppointmentStatus.CANCELLED);
  }
}
