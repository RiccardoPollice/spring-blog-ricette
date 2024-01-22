package org.learning.sping.blog.ricette.repository;

import org.learning.sping.blog.ricette.model.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipesRepository extends JpaRepository<Recipes, Integer> {
    List<Recipes> findByTitleContaining(String searchName);
}
