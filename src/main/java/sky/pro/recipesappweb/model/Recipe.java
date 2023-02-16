package sky.pro.recipesappweb.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.desktop.ScreenSleepEvent;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private String title;
    private List<CookingTime> cookingTimeList;
    private String titleIngredients;
    private List<Ingredient> ingredients;
    private String titleCookingInstructionsSteps;
    private List<Step> cookingInstructionsSteps;
}
