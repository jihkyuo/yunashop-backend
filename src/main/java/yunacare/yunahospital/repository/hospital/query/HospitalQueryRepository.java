package yunacare.yunahospital.repository.hospital.query;

import static yunacare.yunahospital.domain.QDoctor.doctor;
import static yunacare.yunahospital.domain.QHospital.hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HospitalQueryRepository {
  private final JPAQueryFactory queryFactory;

  public List<HospitalQueryResponse> findAllByDto_optimization() {
    List<HospitalQueryResponse> result = findHospitals();

    if (!result.isEmpty()) {
      Map<Long, List<SpecialtyQueryResponse>> specialtyMap = findSpecialtyMap(toHospitalIds(result));
      result.forEach(h -> h.setSpecialties(
          specialtyMap.getOrDefault(h.getHospitalId(), new ArrayList<>())));
    }

    return result;
  }

  private List<HospitalQueryResponse> findHospitals() {
    return queryFactory
        .select(Projections.constructor(HospitalQueryResponse.class,
            hospital.id,
            hospital.name))
        .from(hospital)
        .fetch();
  }

  private List<Long> toHospitalIds(List<HospitalQueryResponse> result) {
    return result.stream()
        .map(HospitalQueryResponse::getHospitalId)
        .collect(Collectors.toList());
  }

  private Map<Long, List<SpecialtyQueryResponse>> findSpecialtyMap(List<Long> hospitalIds) {
    return queryFactory
        .select(Projections.constructor(SpecialtyQueryResponse.class,
            hospital.id,
            doctor.specialty.id,
            doctor.specialty.name))
        .from(hospital)
        .join(hospital.doctors, doctor)
        .join(doctor.specialty)
        .where(hospital.id.in(hospitalIds))
        .fetch()
        .stream()
        .collect(Collectors.groupingBy(SpecialtyQueryResponse::getHospitalId,
            Collectors.mapping(specialty -> new SpecialtyQueryResponse(
                specialty.getSpecialtyId(),
                specialty.getName()),
                Collectors.toList())));
  }
}
