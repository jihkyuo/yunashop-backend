package yunacare.yunahospital.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import yunacare.yunahospital.domain.Appointment;

@Repository
@RequiredArgsConstructor
public class AppointmentRepository {

  private final EntityManager em;

  public void save(Appointment appointment) {
    em.persist(appointment);
  }

  public Appointment findOne(Long appointmentId) {
    return em.find(Appointment.class, appointmentId);
  }
}
