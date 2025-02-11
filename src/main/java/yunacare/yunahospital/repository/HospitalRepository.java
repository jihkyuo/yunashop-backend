package yunacare.yunahospital.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import yunacare.yunahospital.domain.Hospital;

@Repository
@RequiredArgsConstructor
public class HospitalRepository {

  private final EntityManager em;

  public void save(Hospital hospital) {
    em.persist(hospital);
  }

  public Hospital findOne(Long id) {
    return em.find(Hospital.class, id);
  }

  public List<Hospital> findAll() {
    return em.createQuery("select h from Hospital h", Hospital.class).getResultList();
  }

}
