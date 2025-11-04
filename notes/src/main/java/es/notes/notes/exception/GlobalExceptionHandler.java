package es.notes.notes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

// @ControllerAdvice: Le dice a Spring que esta clase manejará excepciones lanzadas por cualquier @Controller.
@ControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler(NoteNotFoundException.class): Este metodo se ejecutará
    // cuando cualquier controlador lance una NoteNotFoundException.
    @ExceptionHandler(NoteNotFoundException.class)
    // El metodo devuelve un ModelAndView, que nos permite especificar tanto
    // la vista (la página HTML) como los datos a mostrar.
    public ModelAndView handleNoteNotFound(NoteNotFoundException ex) {
        // 1. Crear un ModelAndView. Usaremos una nueva plantilla: 'error-404'.
        ModelAndView modelAndView = new ModelAndView("error-404");

        // 2. Añadir el mensaje de error al modelo.
        modelAndView.addObject("errorMessage", ex.getMessage());
        modelAndView.addObject("status", HttpStatus.NOT_FOUND.value()); // Código 404

        // 3. Establecer el código de estado HTTP 404.
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    // Opcional: Manejo de otras excepciones genéricas (ej. 500)
    // Esto atraparía cualquier otra RuntimeException que no hayamos manejado.
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericError(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error-500");
        modelAndView.addObject("errorMessage", "Ocurrió un error interno: " + ex.getMessage());
        modelAndView.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR.value()); // Código 500
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return modelAndView;
    }
}

