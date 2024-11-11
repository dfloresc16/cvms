package com.upiita.msvc_cv.dto;


import java.util.List;

public class CurriculumVitaeDTO {
    private Long cvId;
    private String token;
    private List<CVFieldDTO> cvFieldsDTOs;

    public Long getCvId() {
        return cvId;
    }

    public void setCvId(Long cvId) {
        this.cvId = cvId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<CVFieldDTO> getCvFieldsDTOs() {
        return cvFieldsDTOs;
    }

    public void setCvFieldDTOs(List<CVFieldDTO> cvFieldsDTOs) {
        this.cvFieldsDTOs = cvFieldsDTOs;
    }

    public CurriculumVitaeDTO(Long cvId, String token, List<CVFieldDTO> cvFieldsDTOs) {
        this.cvId = cvId;
        this.token = token;
        this.cvFieldsDTOs = cvFieldsDTOs;
    }

    public CurriculumVitaeDTO() {
    }
}
