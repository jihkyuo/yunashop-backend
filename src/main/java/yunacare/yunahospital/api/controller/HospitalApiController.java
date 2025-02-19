package yunacare.yunahospital.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import yunacare.yunahospital.api.dto.request.CreateHospitalRequest;
import yunacare.yunahospital.api.dto.response.CreateHospitalResponse;
import yunacare.yunahospital.api.dto.response.ResultResponse;
import yunacare.yunahospital.repository.hospital.query.HospitalQueryRepository;
import yunacare.yunahospital.repository.hospital.query.HospitalQueryResponse;
import yunacare.yunahospital.service.HospitalService;

@RestController
@RequiredArgsConstructor
public class HospitalApiController {

  private final HospitalService hospitalService;
  private final HospitalQueryRepository hospitalQueryRepository;

  @GetMapping("/hospitals")
  public ResultResponse<List<HospitalQueryResponse>> findHospitals() {
    return new ResultResponse<>(hospitalQueryRepository.findAllByDto_optimization());
  }

  @PostMapping("/hospitals")
  public CreateHospitalResponse createHospital(@RequestBody @Valid CreateHospitalRequest request) {
    Long id = hospitalService.createHospital(request.getName());
    return new CreateHospitalResponse(id);
  }
}
