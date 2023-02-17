package sky.pro.recipesappweb.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.tomcat.jni.File;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.recipesappweb.services.FilesService;
import sky.pro.recipesappweb.services.RecipesService;
import sky.pro.recipesappweb.model.Recipe;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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


    @Operation(method = "Данные всех рецептов в формате txt.",
            summary = "Данные всех рецептов в формате txt, можете загрузить файл",
            description = "Можно получить данные в формате txt")
    @GetMapping("/AllRecipes")
    public ResponseEntity<Object> getAllRecipesExport() {
        try {
            Path path = recipesService.createAllRecipes();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"AllRecipes.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }
}
