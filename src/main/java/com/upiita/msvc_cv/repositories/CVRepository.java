package com.upiita.msvc_cv.repositories;

import com.upiita.msvc_cv.models.entity.CurriculumVitae;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CVRepository extends CrudRepository<CurriculumVitae, Long> {
    Optional<CurriculumVitae> findByCvId(Long cv_id);
}