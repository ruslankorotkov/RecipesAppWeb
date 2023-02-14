package sky.pro.recipesappweb.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import sky.pro.recipesappweb.model.Ingredient;
import sky.pro.recipesappweb.services.FilesService;
import sky.pro.recipesappweb.services.IngredientService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class IngredientServiceimpl implements IngredientService {

    private Map<Long, Ingredient> ingredientMap = new HashMap<>();
    private long generatedId = 1L;
    private final FilesService filesService;

    public IngredientServiceimpl(FilesService filesService) {
        this.filesService = filesService;
    }

//    @PostConstruct
//    private void bim() {
//        readFromIngredientsFile();
//    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        saveToIngredientsFile();
        return ingredientMap.put(generatedId++, ingredient);
    }

    @Override
    public Optional<Ingredient> getId(Long id) {
        return Optional.ofNullable(ingredientMap.get(id));
    }

    @Override
    public Optional<Ingredient> updateIngredient(Long id, Ingredient ingredient) {
        saveToIngredientsFile();
        return Optional.ofNullable(ingredientMap.replace(id, ingredient));
    }

    @Override
    public Optional<Ingredient> deleteIngredient(Long id) {
        return Optional.ofNullable(ingredientMap.remove(id));
    }

    @Override
    public Map<Long, Ingredient> allIngredients() {
        return ingredientMap;
    }

    private void saveToIngredientsFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            filesService.saveToIngredientsFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromIngredientsFile() {
        try {
            String json = filesService.readFromIngredientsFile();
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Long, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
