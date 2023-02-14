package sky.pro.recipesappweb.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sky.pro.recipesappweb.services.FilesService;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {
    @Value("${path.to.recipes.file}")
    private String recipesFilePath;
    @Value("${name.of.recipes.file}")
    private String recipesFileName;
//    @Value("${path.to.ingredients.file}")
//    private String ingredientsFilePath;
//    @Value("${name.of.ingredients.file}")
//    private String ingredientsFileName;

    @Override
    public boolean saveToFile(String json) {
        try {
            deleteFile();
            Files.writeString(Path.of(recipesFilePath,recipesFileName),json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFile() {
        try {
            return Files.readString(Path.of(recipesFilePath,recipesFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean deleteFile() {
        try {
            Path path = Path.of(recipesFilePath, recipesFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
//    @Override
//    public boolean saveToIngredientsFile(String json) {
//        try {
//            deleteIngredientsFile();
//            Files.writeString(Path.of(ingredientsFilePath,ingredientsFileName),json);
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//    @Override
//    public String readFromIngredientsFile() {
//        try {
//            return Files.readString(Path.of(ingredientsFilePath,ingredientsFileName));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private boolean deleteIngredientsFile() {
//        try {
//            Path path = Path.of(ingredientsFilePath,ingredientsFileName);
//            Files.deleteIfExists(path);
//            Files.createFile(path);
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}
