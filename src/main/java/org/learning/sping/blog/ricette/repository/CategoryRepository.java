package org.learning.sping.blog.ricette.repository;

import org.learning.sping.blog.ricette.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
