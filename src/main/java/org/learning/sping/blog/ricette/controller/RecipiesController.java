package org.learning.sping.blog.ricette.controller;

import jakarta.validation.Valid;
import org.learning.sping.blog.ricette.model.Recipes;
import org.learning.sping.blog.ricette.repository.CategoryRepository;
import org.learning.sping.blog.ricette.repository.RecipesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

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
    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Optional<Recipes> result = recipesRepository.findById(id);
        if (result.isPresent()){
            Recipes recipe = result.get();
            model.addAttribute("recipe", recipe);
            return "/recipes/show";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ricetta with id" + id + "not found");
        }
    }
    @GetMapping("/create")
    public String create(Model model) {
        Recipes recipe = new Recipes();
        model.addAttribute("recipe", recipe);
        model.addAttribute("category", recipesRepository.findAll());
        return "recipes/create";
    }
    @PostMapping("/create")
    public String create2(@Valid @ModelAttribute ("category") Recipes formRecipe, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryRepository.findAll());
            return "category/create";
        }
        Recipes savedRecipe = recipesRepository.save(formRecipe);
        return "redirect:/recipes/show/" + savedRecipe.getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Recipes> result = recipesRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("recipe", result.get());
            model.addAttribute("category", categoryRepository.findAll());
            return "recipes/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "recipe with id" + id + "not found");
        }
    }
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("recipe") Recipes formRecipe, BindingResult bindingResult) {
        Optional<Recipes> result = recipesRepository.findById(id);
        if (result.isPresent()) {
            Recipes recipeToEdit = result.get();
            if (bindingResult.hasErrors()) {
                return "/recipes/edit";
            }
            formRecipe.setUrl(recipeToEdit.getUrl());
            Recipes savedRecipe = recipesRepository.save(formRecipe);
            return "redirect:/recipes/show/{id}";
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "recipe with id" + id + "not found");
        }
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Recipes> result = recipesRepository.findById(id);
        if (result.isPresent()) {
            recipesRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("redirectMessage", "recipe" + result.get().getTitle() + "deleted");
            return "redirect:/recipes";
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "recipe with id" + id + "not found");
        }
    }
}
