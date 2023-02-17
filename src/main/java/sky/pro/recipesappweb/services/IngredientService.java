package sky.pro.recipesappweb.services;

import sky.pro.recipesappweb.model.Ingredient;

import java.util.Map;
import java.util.Optional;

public interface IngredientService {
    Ingredient createIngredient(Ingredient ingredient);

    Optional<Ingredient> getId(Long id);

    Optional<Ingredient> updateIngredient(Long id, Ingredient ingredient);

    Optional<Ingredient> deleteIngredient(Long id);

    Map<Long, Ingredient> allIngredients();

}
