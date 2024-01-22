package org.learning.sping.blog.ricette.controller;

import org.learning.sping.blog.ricette.model.Recipes;
import org.learning.sping.blog.ricette.repository.CategoryRepository;
import org.learning.sping.blog.ricette.repository.RecipesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipiesController {
    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping
    public String list(@RequestParam(name = "keyword", required = false) String searchKeyword, Model model) {
        List<Recipes> recipesList;
        if (searchKeyword != null) {
            recipesList = recipesRepository.findByTitleContaining(searchKeyword);
        } else {
            recipesList = recipesRepository.findAll();
        }
        model.addAttribute("recipesList", recipesList);
        model.addAttribute("preloadSearch", searchKeyword);
        return "recipes/list";
    }
}
