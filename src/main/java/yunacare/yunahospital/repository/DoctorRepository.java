package yunacare.yunahospital.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import yunacare.yunahospital.domain.Doctor;

@Repository
@RequiredArgsConstructor
public class DoctorRepository {

  private final EntityManager em;

  public void save(Doctor doctor) {
    em.persist(doctor);
  }

  public Doctor findOne(Long id) {
    return em.find(Doctor.class, id);
  }

  public List<Doctor> findAll() {
    return em.createQuery("select d from Doctor d", Doctor.class).getResultList();
  }
}
