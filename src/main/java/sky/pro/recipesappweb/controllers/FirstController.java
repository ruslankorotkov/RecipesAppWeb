package sky.pro.recipesappweb.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Контроллер:Информационная панель", description = "CRUD операции и другие эгдпоинты")
@RestController
public class FirstController {
    @GetMapping
    public String applicationLaunched() {
        return "<h1 style=\"text-align: center\">Приложение запущено</h1>";
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Всё хорошо, запрос выполнился"),
            @ApiResponse(responseCode = "404",
                    description = "URL неверный или такого действия нет в веб-приложении"),
            @ApiResponse(responseCode = "500",
                    description = "Во время выполнения запроса произошла ошибка на сервере"),
            @ApiResponse(responseCode = "400",
                    description = "Есть ошибка в параметрах запроса")})
    @Operation(method = "Метод получения информации о проекте (/info)", summary = "Можете получить информацию", description = "Можно получить информацию")
    @GetMapping("/info")
    public String info() {
        return "<h2><center>info:</center></h2>" +
                "<h3><center>имя ученика: Руслан </center></h3>" +
                "<h4><center>название проекта: RecipesAppWeb</center></h4>" +
                "<h5><center>дата создания проекта: 02/02/2023 </center></h5>" +
                "<h6><center>описание проекта: приложение для сайта рецептов</center></h6>";
    }
}
