package sky.pro.recipesappweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    private String name;
    private int weight;
    private String measure;

    @Override
    public String toString() {
        return "" +
                " " + name +
                " " + weight +
                " " + measure;
    }
}
