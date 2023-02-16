package sky.pro.recipesappweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CookingTime {
    private String titleCookingTime;
    private int cookingTime;
    private String titleMeasure;
}
