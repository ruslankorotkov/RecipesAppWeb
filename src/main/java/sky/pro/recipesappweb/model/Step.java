package sky.pro.recipesappweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Step {
    private String step;

    @Override
    public String toString() {
        return "" +
                " " + step;
    }
}
