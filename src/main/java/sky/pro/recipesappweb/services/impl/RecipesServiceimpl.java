package sky.pro.recipesappweb.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import sky.pro.recipesappweb.exception.ValidationException;
import sky.pro.recipesappweb.model.Ingredient;
import sky.pro.recipesappweb.model.Recipe;
import sky.pro.recipesappweb.model.Step;
import sky.pro.recipesappweb.services.FilesService;
import sky.pro.recipesappweb.services.RecipesService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;


@Service
public class RecipesServiceimpl implements RecipesService {

    private static Map<Long, Recipe> recipesMap = new HashMap<>();
    private long generatedId = 1L;
    private final FilesService filesService;

    public RecipesServiceimpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void bom() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        try {
            Validate.notBlank(recipe.getTitle(),
                    "Ошибка валидации названия рецепта / title");
            Validate.notBlank(recipe.getIngredients().iterator().next().getName(),
                    "Ошибка валидации имени ингредиента / name");
            Validate.notBlank(recipe.getIngredients().iterator().next().getMeasure(),
                    "Ошибка валидации измерения количества ингредиента / measure");
            Validate.notBlank(recipe.getCookingInstructionsSteps().stream().iterator().next().getStep(),
                    "Ошибка валидации шагов приготовления / cookingInstructionsSteps");
            Validate.notNull(recipe.getCookingTime(),
                    "Ошибка валидации времени приготовления / cookingTime");
            Validate.notNull(recipe.getIngredients().iterator().next().getWeight(),
                    "Ошибка валидации веса ингредиента / weight");
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
        recipesMap.put(generatedId++, recipe);
        saveToFile();
        return recipe;
    }

    @Override
    public Optional<Recipe> getId(Long id) {
        return Optional.ofNullable(recipesMap.get(id));
    }

    @Override
    public Optional<Recipe> updateRecipe(Long id, Recipe recipe) {
        try {
            Validate.notBlank(recipe.getTitle(),
                    "Ошибка валидации названия рецепта / title");
            Validate.notBlank(recipe.getIngredients().iterator().next().getName(),
                    "Ошибка валидации имени ингредиента / name");
            Validate.notBlank(recipe.getIngredients().iterator().next().getMeasure(),
                    "Ошибка валидации измерения количества ингредиента / measure");
            Validate.notBlank(recipe.getCookingInstructionsSteps().stream().iterator().next().getStep(),
                    "Ошибка валидации шагов приготовления / cookingInstructionsSteps");
            Validate.notNull(recipe.getCookingTime(),
                    "Ошибка валидации времени приготовления / cookingTime");
            Validate.notNull(recipe.getIngredients().iterator().next().getWeight(),
                    "Ошибка валидации веса ингредиента / weight");
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
        Optional.ofNullable(recipesMap.replace(id, recipe));
        saveToFile();
        return Optional.of(recipe);
    }

    @Override
    public Optional<Recipe> deleteRecipe(Long id) {
        return Optional.ofNullable(recipesMap.remove(id));
    }

    @Override
    public Map<Long, Recipe> allRecipes() {
        return recipesMap;
    }

    public void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipesMap);
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void readFromFile() {
        try {
            String json = filesService.readFromFile();
            recipesMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Long, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Path createAllRecipes() throws IOException {
        Path path = filesService.getAllRecipes().toPath();
        String listStop = "*";
        for (Recipe element : recipesMap.values()) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append(element.getTitle()+ "\n"
                        + "\nВремя приготовления: " +
                        element.getCookingTime()).append(" минут\n");

                writer.append("\nИнгредиенты:\n");
                for (Ingredient ele : element.getIngredients()) {
                    writer.append(listStop).append(ele.toString()).append("\n");
                }
                writer.append("\nИнструкция приготовления:\n");
                for (Step elem : element.getCookingInstructionsSteps()) {
                    writer.append(listStop).append(elem.toString()).append("\n");
                }
                writer.append("\n");
            }
        }
        return path;
    }
}


