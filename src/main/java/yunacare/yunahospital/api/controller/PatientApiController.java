package yunacare.yunahospital.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import yunacare.yunahospital.api.dto.request.CreatePatientRequest;
import yunacare.yunahospital.api.dto.request.PatientSearchRequest;
import yunacare.yunahospital.api.dto.response.CreatePatientResponse;
import yunacare.yunahospital.api.dto.response.PaginationResultResponse;
import yunacare.yunahospital.api.dto.response.PatientResponseDto;
import yunacare.yunahospital.api.dto.response.ResultResponse;
import yunacare.yunahospital.domain.Address;
import yunacare.yunahospital.domain.Patient;
import yunacare.yunahospital.repository.patient.query.PatientQueryRepository;
import yunacare.yunahospital.repository.patient.query.PatientQueryResponse;
import yunacare.yunahospital.service.PatientService;

@RestController
@RequiredArgsConstructor
public class PatientApiController {

  private final PatientService patientService;
  private final PatientQueryRepository patientQueryRepository;

  // 환자 조회(일반_레거시)
  @GetMapping("/patients/normal")
  public ResultResponse<List<PatientResponseDto>> findPatientsNormal() {
    List<PatientResponseDto> collect = patientService.findPatients().stream()
        .map(PatientResponseDto::new)
        .collect(Collectors.toList());
    return new ResultResponse<>(collect);
  }

  // 환자 생성
  @PostMapping("/patients")
  public CreatePatientResponse createPatient(@RequestBody @Valid CreatePatientRequest request) {
    Address address = new Address(request.getCity(), request.getStreet(), request.getZipcode());

    Patient patient = new Patient();
    patient.setName(request.getName());
    patient.setPhone(request.getPhone());
    patient.setAddress(address);
    Long id = patientService.join(patient);
    return new CreatePatientResponse(id);
  }

  // 환자 삭제
  @DeleteMapping("/patients/{id}")
  public void deletePatient(@PathVariable Long id) {
    patientService.delete(id);
  }

  // 환자 조회(페이징 dto_최적화)
  @GetMapping("/patients")
  public PaginationResultResponse<PatientQueryResponse> findPatients(
      @Valid PatientSearchRequest request,
      @PageableDefault(size = 10) Pageable pageable) {
    Page<PatientQueryResponse> result = patientQueryRepository.findAllByDto_optimization(request, pageable);
    return new PaginationResultResponse<>(result);
  }

}
