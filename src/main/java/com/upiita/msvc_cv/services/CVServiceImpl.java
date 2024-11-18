package com.upiita.msvc_cv.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upiita.msvc_cv.kafka.KafkaConfiguration;
import com.upiita.msvc_cv.repositories.CVFieldRepository;
import com.upiita.msvc_cv.dto.CVFieldDTO;
import com.upiita.msvc_cv.dto.CVJoinFieldDTO;
import com.upiita.msvc_cv.dto.CurriculumVitaeDTO;
import com.upiita.msvc_cv.models.entity.CVField;
import com.upiita.msvc_cv.models.entity.CurriculumVitae;
import com.upiita.msvc_cv.repositories.CVRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CVServiceImpl implements CVService{
    @Autowired
    private CVRepository cvRepository;

    @Autowired
    private CVFieldRepository cvFieldRepository;

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    private static Logger logger = LoggerFactory.getLogger(CVServiceImpl.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    @Transactional(readOnly = true)
    public List<CurriculumVitaeDTO> listar() {
        List<CurriculumVitae> cvs = (List<CurriculumVitae>) cvRepository.findAll();
        List<CurriculumVitaeDTO> cvDTOs = new ArrayList<>();
        cvs.stream().forEach(cv -> cvDTOs.add(new CurriculumVitaeDTO(cv.getUserId() ,cv.getToken(),cv.getCVFieldsDTOS())));
        return cvDTOs;
    }


    @Override
    @Transactional(readOnly = true)
    public CurriculumVitaeDTO filtrar(Long user_id) {
        CurriculumVitae cv = cvRepository.findByUserId(user_id).
                orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        CurriculumVitaeDTO cvDTO = new CurriculumVitaeDTO(cv.getUserId(), cv.getToken(),cv.getCVFieldsDTOS());
        return cvDTO;
    }

    @Override
    @Transactional
    public CurriculumVitaeDTO createCV(CurriculumVitaeDTO cvDTO, Long userId) {
        CurriculumVitae cv = new CurriculumVitae();
        cv.setToken(cvDTO.getToken());
        cv.setUserId(userId);
        List<CVField> cvFields = getCVFieldsfromDTO(cvDTO);
        cv.setCvFields(cvFields);
        CurriculumVitae cvDB = cvRepository.save(cv);
        CVJoinFieldDTO cvJoinFieldDTO = new CVJoinFieldDTO();
        cvJoinFieldDTO.setUserId(userId);
        cvJoinFieldDTO.setCvFieldsDTOs(cvDB.getCVFieldsDTOS());

        try {
            kafkaTemplate.send("cvFieldsPublishJSON",mapper.writeValueAsString(cvJoinFieldDTO));
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error al parsear los campos del CV");
        }
        return new CurriculumVitaeDTO(cvDB.getUserId(), cvDB.getToken(), cvDB.getCVFieldsDTOS());
    }

    @Override
    @Transactional
    public void deleteCV(Long id) {
        cvRepository.deleteByUserId(id);
    }

    @Override
    @Transactional
    public CurriculumVitaeDTO updateCV(CurriculumVitaeDTO cvDTO, long userId) {
        CurriculumVitae cv = cvRepository.findByUserId(userId).
                orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        cvFieldRepository.deleteCVFieldsByUserId_query(userId);
        List<CVField> cvFields = getCVFieldsfromDTO(cvDTO);
        logger.info(cvFields.stream().toList().toString());
        cv.getCvFields().clear();
        cv.getCvFields().addAll(cvFields);
        logger.info(cv.getCvFields().stream().toList().toString());
        CurriculumVitae cvDB =  cvRepository.save(cv);
        logger.info("save");
        CVJoinFieldDTO cvJoinFieldDTO = new CVJoinFieldDTO();
        cvJoinFieldDTO.setUserId(userId);
        cvJoinFieldDTO.setCvFieldsDTOs(cvDB.getCVFieldsDTOS());
        try {
            kafkaTemplate.send("cvFieldsPublishJSON",mapper.writeValueAsString(cvJoinFieldDTO));
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error al parsear los campos del CV");
        }
        return new CurriculumVitaeDTO(cvDB.getUserId(), cvDB.getToken(), cvDB.getCVFieldsDTOS());
    }

    @Override
    public CVJoinFieldDTO getCVJoinField(Long userId) {
        logger.info("Starting getCVJoinField for userId: {}", userId);

        CurriculumVitae cv = cvRepository.findFirstByUserId(userId)
                .orElseThrow(() -> {
                    logger.error("Curriculum Vitae not found for userId: {}", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Curriculum Vitae not found");
                });

        logger.info("Curriculum Vitae found for userId: {}", userId);

        CVJoinFieldDTO cvJoinFieldDTO = new CVJoinFieldDTO();

        try {
            // Aquí puedes añadir más logs para el proceso, si es necesario
            logger.debug("Populating CVJoinFieldDTO for userId: {}", userId);
            
            cvJoinFieldDTO.setUserId(userId);
            cvJoinFieldDTO.setCvFieldsDTOs(cv.getCVFieldsDTOS());

            logger.info("CVJoinFieldDTO populated successfully for userId: {}", userId);
        } catch (Exception e) {
            logger.error("Error occurred while processing CVJoinFieldDTO for userId: {}. Error: {}", userId, e.getMessage(), e);
        }

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
