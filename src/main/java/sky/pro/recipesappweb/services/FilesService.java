package sky.pro.recipesappweb.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

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
