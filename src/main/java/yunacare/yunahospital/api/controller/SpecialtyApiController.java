package yunacare.yunahospital.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import yunacare.yunahospital.api.dto.request.CreateSpecialtyRequest;
import yunacare.yunahospital.api.dto.response.CreateSpecialtyResponse;
import yunacare.yunahospital.api.dto.response.ResultResponse;
import yunacare.yunahospital.api.dto.response.SpecialtyResponse;
import yunacare.yunahospital.service.SpecialtyService;

@RestController
@RequiredArgsConstructor
public class SpecialtyApiController {

  private final SpecialtyService specialtyService;

  @GetMapping("/specialties")
  public ResultResponse<List<SpecialtyResponse>> findAll() {
    return new ResultResponse<>(specialtyService.findAll());
  }

  @PostMapping("/specialties")
  public CreateSpecialtyResponse createSpecialty(@RequestBody @Valid CreateSpecialtyRequest request) {
    Long specialtyId = specialtyService.createSpecialty(request.getName());
    return new CreateSpecialtyResponse(specialtyId);
  }
}
