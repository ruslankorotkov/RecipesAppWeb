package sky.pro.recipesappweb.services;

public interface FilesService {

    boolean saveToFile(String json);

    String readFromFile();

}
