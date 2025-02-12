package yunacare.yunahospital.repository.patient.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import yunacare.yunahospital.api.dto.request.PatientSearchRequest;

@Repository
@RequiredArgsConstructor
public class PatientQueryRepository {
  private final EntityManager em;

  public List<PatientQueryResponse> findAllByDto_optimization(PatientSearchRequest request) {
    List<PatientQueryResponse> result = findPatients(request);
    Map<Long, List<AppointmentQueryResponse>> appointmentMap = findAppointmentMap(toPatientIds(result));

    result.forEach(p -> p.setAppointments(
        appointmentMap.get(p.getPatientId()) == null ? new ArrayList<>() : appointmentMap.get(p.getPatientId())));
    return result;
  }

  private Map<Long, List<AppointmentQueryResponse>> findAppointmentMap(List<Long> patientIds) {
    List<AppointmentQueryResponse> appointments = em.createQuery(
        "select new yunacare.yunahospital.repository.patient.query.AppointmentQueryResponse(a.patient.id, a.id, a.appointmentDate, a.status) from Appointment a"
            + " where a.patient.id in :patientIds",
        AppointmentQueryResponse.class)
        .setParameter("patientIds", patientIds)
        .getResultList();
    return appointments.stream().collect(Collectors.groupingBy(AppointmentQueryResponse::getPatientId));
  }

  private List<Long> toPatientIds(List<PatientQueryResponse> result) {
    return result.stream().map(PatientQueryResponse::getPatientId).collect(Collectors.toList());
  }

  private List<PatientQueryResponse> findPatients(PatientSearchRequest request) {
    return em.createQuery(
        "select new yunacare.yunahospital.repository.patient.query.PatientQueryResponse(p.id, p.name, p.phone, p.address) from Patient p",
        PatientQueryResponse.class)
        .setFirstResult(request.getOffset())
        .setMaxResults(request.getLimit())
        .getResultList();
  }
}