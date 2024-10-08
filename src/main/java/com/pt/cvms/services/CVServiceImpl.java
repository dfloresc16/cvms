package com.pt.cvms.services;

import com.pt.cvms.repositories.CVFieldRepository;
import com.pt.cvms.dto.CVFieldDTO;
import com.pt.cvms.dto.CVJoinFieldDTO;
import com.pt.cvms.dto.CurriculumVitaeDTO;
import com.pt.cvms.models.entity.CVField;
import com.pt.cvms.models.entity.CurriculumVitae;
import com.pt.cvms.repositories.CVRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CVServiceImpl implements CVService{
    @Autowired
    private CVRepository cvRepository;

    @Autowired
    private CVFieldRepository cvFieldRepository;

    private static Logger logger = LoggerFactory.getLogger(CVServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public List<CurriculumVitaeDTO> listar() {
        List<CurriculumVitae> cvs = (List<CurriculumVitae>) cvRepository.findAll();
        List<CurriculumVitaeDTO> cvDTOs = new ArrayList<>();
        cvs.stream().forEach(cv -> cvDTOs.add(new CurriculumVitaeDTO(cv.getCvId(),cv.getToken(),cv.getCVFieldsDTOS())));
        return cvDTOs;
    }


    @Override
    @Transactional(readOnly = true)
    public CurriculumVitaeDTO filtrar(Long cv_id) {
        CurriculumVitae cv = cvRepository.findByCvId(cv_id).
                orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        CurriculumVitaeDTO cvDTO = new CurriculumVitaeDTO(cv.getCvId(),cv.getToken(),cv.getCVFieldsDTOS());
        return cvDTO;
    }

    @Override
    @Transactional
    public CurriculumVitaeDTO createCV(CurriculumVitaeDTO cvDTO) {
        CurriculumVitae cv = new CurriculumVitae();
        //cv.setCvId(cvDTO.getCvId());
        cv.setToken(cvDTO.getToken());
        List<CVField> cvFields = getCVFieldsfromDTO(cvDTO);
        cv.setCvFields(cvFields);
        CurriculumVitae cvDB = cvRepository.save(cv);
        return new CurriculumVitaeDTO(cvDB.getCvId(), cvDB.getToken(), cvDB.getCVFieldsDTOS());
    }

    @Override
    @Transactional
    public void deleteCV(Long id) {
        cvRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CurriculumVitaeDTO updateCV(CurriculumVitaeDTO cvDTO, long cv_id) {
        CurriculumVitae cv = cvRepository.findByCvId(cv_id).
                orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        cvFieldRepository.deleteCVFieldsByCVId_query(cv_id);
        List<CVField> cvFields = getCVFieldsfromDTO(cvDTO);
        logger.info(cvFields.stream().toList().toString());
        cv.getCvFields().clear();
        cv.getCvFields().addAll(cvFields);
        logger.info(cv.getCvFields().stream().toList().toString());
        CurriculumVitae cvDB =  cvRepository.save(cv);
        logger.info("save");
        return new CurriculumVitaeDTO(cvDB.getCvId(),cvDB.getToken(), cvDB.getCVFieldsDTOS());
    }

    @Override
    public CVJoinFieldDTO getCVJoinField(Long cv_id){
        CurriculumVitae cv = cvRepository.findByCvId(cv_id).
                orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        CVJoinFieldDTO cvJoinFieldDTO = new CVJoinFieldDTO();
        /*List<String> cvFields = getCVFieldsByCVId(cv);
        List<String> cvLevels = getCVLevelsByCVId(cv);
        List<String> cvCategories = getCVCategoriesByCVId(cv);*/
        cvJoinFieldDTO.setCvId(cv_id);
        cvJoinFieldDTO.setCvFieldsDTOs(cv.getCVFieldsDTOS());
        return cvJoinFieldDTO;
    }

    public List<String> getCVFieldsByCVId(CurriculumVitae cv) {
        List<String> cvFields = new ArrayList<>();
        cvFields =  cv.getCvFields().stream().map(cvField -> cvField.getField())
                .collect(Collectors.toList());
        return cvFields;
    }

    public List<String> getCVLevelsByCVId(CurriculumVitae cv) {
        List<String> cvLevels = new ArrayList<>();
        cvLevels =  cv.getCvFields().stream().map(cvField -> cvField.getLevel())
                .collect(Collectors.toList());
        return cvLevels;
    }

    public List<String> getCVCategoriesByCVId(CurriculumVitae cv) {
        List<String> cvCategories = new ArrayList<>();
        cvCategories =  cv.getCvFields().stream().map(cvField -> cvField.getCatalogCategory())
                .collect(Collectors.toList());
        return cvCategories;
    }

    public List<CVField> getCVFieldsfromDTO(CurriculumVitaeDTO cvDTO){
        List<CVField> cvFields = new ArrayList<>();
        for(CVFieldDTO cvFieldDTO:cvDTO.getCvFieldsDTOs()){
            CVField cvField = new CVField();
            cvField.setField(cvFieldDTO.getField());
            cvField.setLevel(cvFieldDTO.getLevel());
            cvField.setCatalogCategory(cvFieldDTO.getCategory());
            cvFields.add(cvField);
        }
        return cvFields;
    }
}
