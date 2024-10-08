package com.pt.cvms.services;

import com.pt.cvms.dto.CVJoinFieldDTO;
import com.pt.cvms.dto.CurriculumVitaeDTO;

import java.util.List;

public interface CVService {
    List<CurriculumVitaeDTO> listar();

    CurriculumVitaeDTO filtrar(Long cv_id);

    CurriculumVitaeDTO createCV(CurriculumVitaeDTO cv);

    void deleteCV(Long id);

    CurriculumVitaeDTO updateCV(CurriculumVitaeDTO cv, long cv_id);

    CVJoinFieldDTO getCVJoinField(Long cv_id);


}
