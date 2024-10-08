package com.pt.cvms.controllers;

import com.pt.cvms.dto.CurriculumVitaeDTO;
import com.pt.cvms.dto.CVJoinFieldDTO;
import com.pt.cvms.dto.response.GenericResponseDTO;
import com.pt.cvms.models.commons.CommonController;
import com.pt.cvms.services.CVService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/cv/")
public class CVController extends CommonController{
    private static Logger logger = LoggerFactory.getLogger(CVController.class);
    @Autowired
    private CVService cvService;

    @GetMapping
    public ResponseEntity<GenericResponseDTO<List<CurriculumVitaeDTO>>> listar(){
        try{
            logger.info("Execute getCVFields() " );
            return ResponseEntity.ok().body(new GenericResponseDTO<>(
                    CommonController.SUCCESS, HttpStatus.OK.value(), null, null, null,cvService.listar()));
        }catch(ResponseStatusException ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(ERROR, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString(),
                    ex.getMessage(), "service execute", null), HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ex.getMessage(), "service execute", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createCV")
    public ResponseEntity<GenericResponseDTO<CurriculumVitaeDTO>> createCV(@Valid @RequestBody CurriculumVitaeDTO cvDTO){
        try{
            logger.info("Execute getDataCV() " );
            return ResponseEntity.ok().body(new GenericResponseDTO<>(
                    CommonController.SUCCESS, HttpStatus.OK.value(), null, null, null,cvService.createCV(cvDTO)));
        }catch(ResponseStatusException ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(ERROR, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString(),
                    ex.getMessage(), "service execute", null), HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ex.getMessage(), "service execute", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDataCV/{cv_id}")
    public ResponseEntity<GenericResponseDTO<CurriculumVitaeDTO>> getDataCV(@PathVariable Long cv_id){
        try{
            logger.info("Execute getDataCV() " );
            return ResponseEntity.ok().body(new GenericResponseDTO<>(
                    CommonController.SUCCESS, HttpStatus.OK.value(), null, null, null,cvService.filtrar(cv_id)));
        }catch(ResponseStatusException ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(ERROR, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString(),
                    ex.getMessage(), "service execute", null), HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ex.getMessage(), "service execute", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteCV/{cv_id}")
    public ResponseEntity<GenericResponseDTO<?>> deleteCV(@PathVariable Long cv_id){
        try{
            logger.info("Execute deleteCV() " );
            cvService.deleteCV(cv_id);
            return ResponseEntity.ok().body(new GenericResponseDTO<>(
                    CommonController.SUCCESS, HttpStatus.OK.value(), null, null, null,null));
        }catch(ResponseStatusException ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(ERROR, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString(),
                    ex.getMessage(), "service execute", null), HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ex.getMessage(), "service execute", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateCV/{cv_id}")
    public ResponseEntity<GenericResponseDTO<CurriculumVitaeDTO>> updateCV(@Valid @RequestBody CurriculumVitaeDTO cvDTO, @PathVariable long cv_id){
        try{
            logger.info("Execute updateCV()" );
            return ResponseEntity.ok().body(new GenericResponseDTO<>(
                    CommonController.SUCCESS, HttpStatus.OK.value(), null, null, null,cvService.updateCV(cvDTO,cv_id)));
        }catch(ResponseStatusException ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(ERROR, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString(),
                    ex.getMessage(), "service execute", null), HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ex.getMessage(), "service execute", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCVFields/{cv_id}")
    public ResponseEntity<GenericResponseDTO<CVJoinFieldDTO>> getCVFieldsByCVId(@PathVariable Long cv_id){
        try{
            logger.info("Execute getCVFields() " );
            return ResponseEntity.ok().body(new GenericResponseDTO<>(
                    CommonController.SUCCESS, HttpStatus.OK.value(), null, null, null,cvService.getCVJoinField(cv_id)));
        }catch(ResponseStatusException ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(ERROR, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString(),
                    ex.getMessage(), "service execute", null), HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    ex.getMessage(), "service execute", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
