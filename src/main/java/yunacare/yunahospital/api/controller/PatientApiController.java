package yunacare.yunahospital.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import yunacare.yunahospital.api.dto.request.CreatePatientRequest;
import yunacare.yunahospital.api.dto.response.CreatePatientResponse;
import yunacare.yunahospital.api.dto.response.PatientResponseDto;
import yunacare.yunahospital.api.dto.response.ResultResponse;
import yunacare.yunahospital.domain.Address;
import yunacare.yunahospital.domain.Patient;
import yunacare.yunahospital.service.PatientService;

@RestController
@RequiredArgsConstructor
public class PatientApiController {

  private final PatientService patientService;

  // 환자 조회
  @GetMapping("/patients")
  public ResultResponse<List<PatientResponseDto>> findPatients() {
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
}
