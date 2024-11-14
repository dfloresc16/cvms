package com.upiita.msvc_cv.dto;

import java.util.List;

public class CVJoinFieldDTO {
    private Long userId;

    private List<CVFieldDTO> cvFieldsDTOs;

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public List<CVFieldDTO> getCvFieldsDTOs() {
        return cvFieldsDTOs;
    }

    public void setCvFieldsDTOs(List<CVFieldDTO> cvFieldsDTOs) {
        this.cvFieldsDTOs = cvFieldsDTOs;
    }
}
