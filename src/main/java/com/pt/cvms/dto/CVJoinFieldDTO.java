package com.pt.cvms.dto;

import java.util.List;

public class CVJoinFieldDTO {
    private Long cvId;

    private List<CVFieldDTO> cvFieldsDTOs;
    /*private List<String> fields;

    private List<String> levels;

    private List<String> categories;*/

    public Long getCvId() {
        return cvId;
    }

    public void setCvId(Long cvId) {
        this.cvId = cvId;
    }

    /*public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getLevels() {
        return levels;
    }

    public void setLevels(List<String> levels) {
        this.levels = levels;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }*/

    public List<CVFieldDTO> getCvFieldsDTOs() {
        return cvFieldsDTOs;
    }

    public void setCvFieldsDTOs(List<CVFieldDTO> cvFieldsDTOs) {
        this.cvFieldsDTOs = cvFieldsDTOs;
    }
}
