package yunacare.yunahospital.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import yunacare.yunahospital.domain.Patient;
import yunacare.yunahospital.repository.PatientRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientService {

  private final PatientRepository patientRepository;

  public Long join(Patient patient) {
    patientRepository.save(patient);
    return patient.getId();
  }

  @Transactional(readOnly = true)
  public List<Patient> findPatients() {
    return patientRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Patient findOne(Long patientId) {
    return patientRepository.findOne(patientId);
  }

  public void delete(Long patientId) {
    patientRepository.delete(patientId);
  }
}
