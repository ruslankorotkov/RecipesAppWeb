package sky.pro.recipesappweb.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Step {
    private String step;

    @Override
    public String toString() {
        return "" +
                " " + step;
    }
}
