package com.upiita.msvc_cv.models.entity;

import com.upiita.msvc_cv.dto.CVFieldDTO;
import com.upiita.msvc_cv.models.User;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="CurriculumVitae")
public class CurriculumVitae {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cvId;

    @NotNull
    private String token;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="cvId")//crea esta fk en la otra tabla (CVField) y de esta forma se relacionan
    //mapeo de un CV a muchos cvField; con cascade significa que se crean y destruyen elementos en orden; con orphanRemoval significa que no permite que se quede un id solo
    //@Transient
    private List<CVField> cvFields;

    @NotNull
    private Long userId;

    private static Logger logger = LoggerFactory.getLogger(CurriculumVitae.class);

    public CurriculumVitae() {
        this.cvFields = new ArrayList<>();
    }

    public Long getCvId() {
        return cvId;
    }

    public void setCvId(Long cv_id) {
        this.cvId = cv_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<CVField> getCvFields() {
        return cvFields;
    }

    public void setCvFields(List<CVField> cvFields) {
        this.cvFields = cvFields;
    }

    //Métodos adicionales para dar funcionalidad a la relación CV-CVField
    public void addCVField(CVField cvField){
        cvFields.add(cvField);
    }

    public void removeCVField(CVField cvField){
        cvFields.remove(cvField);
    }

    public List<CVFieldDTO> getCVFieldsDTOS(){
        List<CVFieldDTO> cvFieldsDTOs = new ArrayList<>();
        for(CVField cvField:this.getCvFields()){
            CVFieldDTO cvFieldDTO = new CVFieldDTO();
            cvFieldDTO.setField(cvField.getField());
            cvFieldDTO.setLevel(cvField.getLevel());
            cvFieldDTO.setCategory(cvField.getCatalogCategory());
            //logger.info(cvField.getCatalogCategory());
            cvFieldsDTOs.add(cvFieldDTO);
        }
        return cvFieldsDTOs;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        CurriculumVitae.logger = logger;
    }
}
