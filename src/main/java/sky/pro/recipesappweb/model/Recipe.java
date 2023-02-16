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
    private int cookingTime;
    private List<Ingredient> ingredients;

    @Override
    public String toString() {
        return "" +
                " " + title +
                " " + cookingTime + " минут. " +
                " " + ingredients +
                " " + cookingInstructionsSteps;
    }

    private List<Step> cookingInstructionsSteps;
}
