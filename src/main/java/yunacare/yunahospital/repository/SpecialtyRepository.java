package yunacare.yunahospital.repository;

import static yunacare.yunahospital.domain.QSpecialty.specialty;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import yunacare.yunahospital.api.dto.response.SpecialtyResponse;
import yunacare.yunahospital.domain.Specialty;

@Repository
@RequiredArgsConstructor
public class SpecialtyRepository {

  private final EntityManager em;
  private final JPAQueryFactory queryFactory;

  public void save(Specialty specialty) {
    em.persist(specialty);
  }

  public List<SpecialtyResponse> findAll() {
    return queryFactory
        .select(Projections.constructor(SpecialtyResponse.class,
            specialty.id,
            specialty.name))
        .from(specialty)
        .fetch();
  }

}
