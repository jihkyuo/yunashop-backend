package yunacare.yunahospital.repository.patient.query;

import static org.springframework.util.StringUtils.hasText;
import static yunacare.yunahospital.domain.QAppointment.appointment;
import static yunacare.yunahospital.domain.QPatient.patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import yunacare.yunahospital.api.dto.request.PatientSearchRequest;

@Repository
@RequiredArgsConstructor
public class PatientQueryRepository {
  private final JPAQueryFactory queryFactory;

  public List<PatientQueryResponse> findAllByDto_optimization(PatientSearchRequest request) {
    List<PatientQueryResponse> result = findPatients(request);

    if (!result.isEmpty()) {
      Map<Long, List<AppointmentQueryResponse>> appointmentMap = findAppointmentMap(toPatientIds(result));

      result.forEach(p -> p.setAppointments(
          appointmentMap.getOrDefault(p.getPatientId(), new ArrayList<>())));
    }
    return result;
  }

  private Map<Long, List<AppointmentQueryResponse>> findAppointmentMap(List<Long> patientIds) {
    List<AppointmentQueryResponse> appointments = queryFactory
        .select(Projections.constructor(AppointmentQueryResponse.class,
            appointment.patient.id,
            appointment.id,
            appointment.appointmentDate,
            appointment.status))
        .from(appointment)
        .where(appointment.patient.id.in(patientIds))
        .fetch();

    return appointments.stream().collect(Collectors.groupingBy(AppointmentQueryResponse::getPatientId));
  }

  private List<Long> toPatientIds(List<PatientQueryResponse> result) {
    return result.stream()
        .map(PatientQueryResponse::getPatientId)
        .collect(Collectors.toList());
  }

  private List<PatientQueryResponse> findPatients(PatientSearchRequest request) {
    return queryFactory
        .select(Projections.constructor(PatientQueryResponse.class,
            patient.id,
            patient.name,
            patient.phone,
            patient.address))
        .from(patient)
        .where(
            nameContains(request.getName()),
            phoneContains(request.getPhone()),
            cityContains(request.getCity()))
        .offset(request.getOffset())
        .limit(request.getLimit())
        .fetch();
  }

  // 검색 조건 메서드
  private BooleanExpression nameContains(String name) {
    return hasText(name) ? patient.name.contains(name) : null;
  }

  private BooleanExpression phoneContains(String phone) {
    return hasText(phone) ? patient.phone.contains(phone) : null;
  }

  private BooleanExpression cityContains(String city) {
    return hasText(city) ? patient.address.city.contains(city) : null;
  }
}