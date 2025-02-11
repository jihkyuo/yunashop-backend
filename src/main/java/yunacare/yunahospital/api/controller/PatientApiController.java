package yunacare.yunahospital.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import yunacare.yunahospital.api.dto.response.PatientResponseDto;
import yunacare.yunahospital.api.dto.response.ResultResponse;
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
}
