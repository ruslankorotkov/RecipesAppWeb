package sky.pro.recipesappweb.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String e) {
        super("Ошибка наличия данных. " + e);
    }

}
