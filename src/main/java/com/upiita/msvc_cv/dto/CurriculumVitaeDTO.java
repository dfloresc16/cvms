package com.upiita.msvc_cv.dto;


import java.util.List;

public class CurriculumVitaeDTO {
    private Long userId;
    private String token;
    private List<CVFieldDTO> cvFieldsDTOs;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public CurriculumVitaeDTO(Long userId, String token, List<CVFieldDTO> cvFieldsDTOs) {
        this.userId = userId;
        this.token = token;
        this.cvFieldsDTOs = cvFieldsDTOs;
    }

    public CurriculumVitaeDTO() {
    }
}
