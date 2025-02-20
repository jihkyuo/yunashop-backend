package yunacare.yunahospital.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import yunacare.yunahospital.api.dto.response.SpecialtyResponse;
import yunacare.yunahospital.domain.Specialty;
import yunacare.yunahospital.repository.SpecialtyRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class SpecialtyService {

  private final SpecialtyRepository specialtyRepository;

  public Long createSpecialty(String name) {
    Specialty specialty = Specialty.createSpecialty(name);
    specialtyRepository.save(specialty);
    return specialty.getId();
  }

  public List<SpecialtyResponse> findAll() {
    return specialtyRepository.findAll();
  }
  
}
