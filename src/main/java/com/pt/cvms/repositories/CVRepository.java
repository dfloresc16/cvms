package com.pt.cvms.repositories;

import com.pt.cvms.models.entity.CurriculumVitae;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CVRepository extends CrudRepository<CurriculumVitae, Long> {
    Optional<CurriculumVitae> findByCvId(Long cv_id);
}
