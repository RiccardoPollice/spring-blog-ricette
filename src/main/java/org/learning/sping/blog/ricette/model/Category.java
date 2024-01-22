package org.learning.sping.blog.ricette.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private List<Recipes> recipesList;
}
