package sky.pro.recipesappweb.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.recipesappweb.exception.ValidationException;
import sky.pro.recipesappweb.services.FilesService;

import java.io.*;

@Tag(name = "Контроллер export/import", description = "CRUD операции и другие эгдпоинты для выгрузки и загрузки файлов")
@RestController
@RequestMapping("/files")
public class FilesController {
    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @Operation(method = "export файла рецепта.", summary = "Можете загрузить (принять) файл",
            description = "Можно получить файл")
    @GetMapping(value = "/export-recipes")
    public ResponseEntity<InputStreamResource> dowloadRecipeFile() throws FileNotFoundException {
        File file = filesService.getRecipesFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipesLog.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(method = "import файла рецепта.", summary = "Можете выгрузить (отправить) файл",
            description = "Можно отправить файл")
    @PostMapping(value = "/import-recipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipeFile(@RequestParam MultipartFile file) {
        filesService.deleteFile();
        File recipesFile = filesService.getRecipesFile();
        try (FileOutputStream fos = new FileOutputStream(recipesFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new ValidationException("Ошибка при выгрузке файла / uploadRecipeFile() ");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        try (BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
//             FileOutputStream fos = new FileOutputStream(recipesFile);
//             BufferedOutputStream bos = new BufferedOutputStream(fos);) {
//            byte[]buffer = new byte[1024];
//            while (bis.read(buffer)>0){
//                bos.write(buffer);
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Operation(method = "export файла рецепта.", summary = "Можете загрузить (принять) файл",
            description = "Можно получить файл")
    @GetMapping(value = "/export-ingredients")
    public ResponseEntity<InputStreamResource> dowloadIngredientsFile() throws FileNotFoundException {
        File file = filesService.getIngredientsFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"IngredientsLog.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(method = "import файла рецепта.", summary = "Можете выгрузить (отправить) файл",
            description = "Можно отправить файл")
    @PostMapping(value = "/import-ingredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadIngredientsFile(@RequestParam MultipartFile file) {
        filesService.deleteIngredientsFile();
        File ingredientsFile = filesService.getIngredientsFile();
        try (FileOutputStream fos = new FileOutputStream(ingredientsFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new ValidationException("Ошибка при выгрузке файла / uploadIngredientsFile ");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
