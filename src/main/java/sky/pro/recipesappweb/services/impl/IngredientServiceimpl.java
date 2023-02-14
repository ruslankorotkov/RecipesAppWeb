package sky.pro.recipesappweb.services.impl;

import org.springframework.stereotype.Service;
import sky.pro.recipesappweb.model.Ingredient;

import sky.pro.recipesappweb.services.FilesService;
import sky.pro.recipesappweb.services.IngredientService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class IngredientServiceimpl implements IngredientService {

    private Map<Long, Ingredient> ingredientMap = new HashMap<>();
    private long generatedId = 1L;
    private final FilesService filesService;

    public IngredientServiceimpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientMap.put(generatedId++, ingredient);
    }

    @Override
    public Optional<Ingredient> getId(Long id) {
        return Optional.ofNullable(ingredientMap.get(id));
    }

    @Override
    public Optional<Ingredient> updateIngredient(Long id, Ingredient ingredient) {
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
}
