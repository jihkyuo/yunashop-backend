package yunacare.yunahospital.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import yunacare.yunahospital.domain.Appointment;
import yunacare.yunahospital.domain.Doctor;
import yunacare.yunahospital.domain.Patient;
import yunacare.yunahospital.repository.AppointmentRepository;
import yunacare.yunahospital.repository.DoctorRepository;
import yunacare.yunahospital.repository.PatientRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {
  private final AppointmentRepository appointmentRepository;
  private final PatientRepository patientRepository;
  private final DoctorRepository doctorRepository;

  // 예약
  public Long makeAppointment(Long patientId, Long doctorId) {
    // 엔티티 조회
    Patient patient = patientRepository.findOne(patientId);
    Doctor doctor = doctorRepository.findOne(doctorId);

    // 예약 생성
    Appointment appointment = Appointment.createAppointment(patient, doctor);

    // 예약 저장
    appointmentRepository.save(appointment);

    return appointment.getId();
  }

  // 예약 취소
  public void cancelAppointment(Long appointmentId) {
    Appointment appointment = appointmentRepository.findOne(appointmentId);
    appointment.cancel();
  }
}
