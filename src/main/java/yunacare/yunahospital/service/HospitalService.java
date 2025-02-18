package yunacare.yunahospital.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import yunacare.yunahospital.domain.Hospital;
import yunacare.yunahospital.repository.HospitalRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class HospitalService {

  private final HospitalRepository hospitalRepository;

  public Long createHospital(String name) {
    Hospital hospital = Hospital.createHospital(name);
    hospitalRepository.save(hospital);
    return hospital.getId();
  }

  @Transactional(readOnly = true)
  public Hospital findOne(Long hospitalId) {
    return hospitalRepository.findOne(hospitalId);
  }

  @Transactional(readOnly = true)
  public List<Hospital> findHospitals() {
    return hospitalRepository.findAll();
  }

  public void delete(Long hospitalId) {
    hospitalRepository.delete(hospitalId);
  }

}
