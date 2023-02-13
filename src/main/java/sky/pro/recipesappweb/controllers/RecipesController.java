package sky.pro.recipesappweb.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.recipesappweb.exception.ValidationException;
import sky.pro.recipesappweb.services.RecipesService;
import sky.pro.recipesappweb.model.Recipe;

import java.util.Map;

@Tag(name = "Рецепты", description = "CRUD операции и другие эгдпоинты для работы с рецептами")
@RestController
@RequestMapping("/recipe")
public class RecipesController {
    private final RecipesService recipesService;

    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Информация успешно была получена",
            content = {@Content(mediaType = "application/json")})})
    @Operation(method = "Добавление рецепта.",
            summary = "Добавление рецепта.", description = "Можно ввести информацию")
    @PostMapping("/")
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
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
                    "Ошибка валидации веса ингредиента / ingredient");
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
        return ResponseEntity.ok(recipesService.createRecipe(recipe));
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Информация успешно была получена",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Информация не была получена",
                    content = {@Content(mediaType = "application/json")})})
    @Operation(method = "Получение рецепта по id.",
            summary = "Получение рецепта по id.", description = "Можно получить информацию")
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        Validate.notBlank(getRecipe(id).toString(), "Строка не может быть пустая или значение null");
        return ResponseEntity.of(recipesService.getId(id));
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Информация успешно была получена",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Информация не была получена",
                    content = {@Content(mediaType = "application/json")})})
    @Operation(method = "Редактирование рецепта по id.",
            summary = "Редактирование рецепта по id.", description = "Можно изменить информацию")
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        Validate.notBlank(recipe.toString(), "Строка не может быть пустая или значение null");
        return ResponseEntity.of(recipesService.updateRecipe(id, recipe));
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Информация успешно была получена",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Информация не была получена",
                    content = {@Content(mediaType = "application/json")})})
    @Operation(method = "Удаление рецепта по id.", summary = "Удаление рецепта по id.",
            description = "Можно удалить информацию")
    @DeleteMapping("/{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id) {
        return ResponseEntity.of(recipesService.deleteRecipe(id));
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Информация успешно была получена",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Информация не была получена",
                    content = {@Content(mediaType = "application/json")})})
    @Operation(method = "Получение списка всех рецептов.", summary = "Получение списка всех рецептов.",
            description = "Можно получить информацию")
    @GetMapping("/")
    public ResponseEntity<Map<Long, Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipesService.allRecipes());
    }
}
