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
import sky.pro.recipesappweb.services.IngredientService;
import sky.pro.recipesappweb.model.Ingredient;

import java.util.Map;

@Tag(name = "Ингредиенты", description = "CRUD операции и другие эгдпоинты для работы с ингредиентами")
@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Информация была получена", content = {@Content(mediaType = "application/json")})})
    @Operation(method = "Добавление ингредиента.", summary = "Можете ввести информацию об новом ингредиент",
            description = "Можно ввести информацию")
    @PostMapping("/")
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        try {
            Validate.notBlank(ingredient.getName(),
                    "Ошибка валидации названия ингредиента / name");
            Validate.notBlank(ingredient.getMeasure(),
                    "Ошибка валидации измерения количества ингредиента / measure");
            Validate.notNull(ingredient.getWeight(),
                    "Ошибка валидации веса ингредиента / weight");
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
        return ResponseEntity.ok(ingredientService.createIngredient(ingredient));
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Информация была получена", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Информация не была получена",
                    content = {@Content(mediaType = "application/json")})})
    @Operation(method = "Получение информации об ингредиенте по id.",
            summary = "Можете получить информацию об ингредиенте по id", description = "Можно получить информацию")
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable Long id) {
        return ResponseEntity.of(ingredientService.getId(id));
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Информация была получена", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Информация не была получена",
                    content = {@Content(mediaType = "application/json")})})
    @Operation(method = "Редактирование ингредиента по id.",
            summary = "Редактирование ингредиента по id.", description = "Можно изменить информацию")
    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        try {
            Validate.notBlank(ingredient.getName(),
                    "Ошибка валидации названия ингредиента / name");
            Validate.notBlank(ingredient.getMeasure(),
                    "Ошибка валидации измерения количества ингредиента / measure");
            Validate.notNull(ingredient.getWeight(),
                    "Ошибка валидации веса ингредиента / weight");
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
        return ResponseEntity.of(ingredientService.updateIngredient(id, ingredient));
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Информация была получена",
            content = {@Content(mediaType = "application/json")}), @ApiResponse(responseCode = "404",
            description = "Информация не была получена",
            content = {@Content(mediaType = "application/json")})})
    @Operation(method = "Удаление ингредиента.", summary = "Удаление ингредиента.",
            description = "Можно удалить информацию")
    @DeleteMapping("/{id}")
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable Long id) {
        return ResponseEntity.of(ingredientService.deleteIngredient(id));
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Информация была получена",
            content = {@Content(mediaType = "application/json")}), @ApiResponse(responseCode = "404",
            description = "Информация не была получена",
            content = {@Content(mediaType = "application/json")})})
    @Operation(method = "Получение полного списка ингредиентов.",
            summary = "Получение полного списка ингредиентов.", description = "Можно получить информацию")
    @GetMapping("/")
    public ResponseEntity<Map<Long, Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.allIngredients());
    }
}

