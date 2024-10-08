package com.pt.cvms.repositories;

import com.pt.cvms.models.entity.CVField;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CVFieldRepository extends CrudRepository<CVField, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM CVField cf WHERE cf.cv.cvId = ?1")
    void deleteCVFieldsByCVId_query(Long cv_id);
}
