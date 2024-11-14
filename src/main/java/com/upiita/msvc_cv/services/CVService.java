package com.upiita.msvc_cv.services;

import com.upiita.msvc_cv.dto.CVJoinFieldDTO;
import com.upiita.msvc_cv.dto.CurriculumVitaeDTO;

import java.util.List;

public interface CVService {
    List<CurriculumVitaeDTO> listar();

    CurriculumVitaeDTO filtrar(Long cv_id);

    CurriculumVitaeDTO createCV(CurriculumVitaeDTO cv, Long userId);

    void deleteCV(Long id);

    CurriculumVitaeDTO updateCV(CurriculumVitaeDTO cv, long cv_id);

    CVJoinFieldDTO getCVJoinField(Long cv_id);


}

