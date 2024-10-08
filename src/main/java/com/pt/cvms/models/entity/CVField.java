package com.pt.cvms.models.entity;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name="CVField")
public class CVField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cvFieldId;

    @NotNull
    private String field;

    private String level;

    @Column(name="catalogCategory")
    private String catalogCategory;

    @ManyToOne
    @JoinColumn(name = "cvId")
    private CurriculumVitae cv;
    /*@Column(name="cv_id")
    private Long cvId;*/

    public Long getCvFieldId() {
        return cvFieldId;
    }

    public void setCvFieldId(Long cvFieldId) {
        this.cvFieldId = cvFieldId;
    }

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

    public String getCatalogCategory() {
        return catalogCategory;
    }

    public void setCatalogCategory(String catalogCategory) {
        this.catalogCategory = catalogCategory;
    }

    /*public Long getCvId() {
        return cvId;
    }

    public void setCvId(long cvId) {
        this.cvId = cvId;
    }*/

    public CVField(Long cvFieldId, String field, String level, String catalogCategory) {
        this.cvFieldId = cvFieldId;
        this.field = field;
        this.level = level;
        this.catalogCategory = catalogCategory;
    }

    public CVField() {
    }
}
