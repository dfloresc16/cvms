package com.upiita.msvc_cv.dto;

public class CVFieldDTO {
    private String field;
    private String level;
    private String category;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public CVFieldDTO(String field, String level, String category) {
        this.field = field;
        this.level = level;
        this.category = category;
    }

    public CVFieldDTO() {
    }
}
