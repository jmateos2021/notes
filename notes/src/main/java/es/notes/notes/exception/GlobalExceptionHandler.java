package es.notes.notes.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

// @ControllerAdvice: Le dice a Spring que esta clase manejará excepciones lanzadas por cualquier @Controller.
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // @ExceptionHandler(NoteNotFoundException.class): Este metodo se ejecutará
    // cuando cualquier controlador lance una NoteNotFoundException.

    // El metodo devuelve un ModelAndView, que nos permite especificar tanto
    // la vista (la página HTML) como los datos a mostrar.
    @ExceptionHandler(NoteNotFoundException.class)
    public ModelAndView handleNoteNotFound(NoteNotFoundException ex) {

        //Esto es para añadir en el log el error que nos ha salido, para ir teniendo un historico de los errores.
        logger.warn("Note not found: {}", ex.getMessage());

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
        System.out.println("Entró al handler de General");
        ModelAndView modelAndView = new ModelAndView("error-500");
        modelAndView.addObject("errorMessage", "An internal error occurred: " + ex.getMessage());
        modelAndView.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR.value()); // Código 500
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return modelAndView;
    }
}

