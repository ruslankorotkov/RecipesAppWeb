package sky.pro.recipesappweb.services;

import java.io.File;

public interface FilesService {

    boolean saveToFile(String json);

    String readFromFile();

    boolean deleteFile();

    boolean saveToIngredientsFile(String json);

    String readFromIngredientsFile();

    boolean deleteIngredientsFile();

    File getRecipesFile();

    File getIngredientsFile();
}
