package com.pt.cvms.repositories;

import com.pt.cvms.models.entity.CatalogCategory;
import org.springframework.data.repository.CrudRepository;

public interface CatalogCategoryRepository extends CrudRepository<CatalogCategory, Long> {
}
