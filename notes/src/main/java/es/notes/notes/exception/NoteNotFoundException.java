package es.notes.notes.exception;

public class NoteNotFoundException extends RuntimeException {

    // Constructor que recibe el ID y construye un mensaje descriptivo
    public NoteNotFoundException(Long id) {
        super("No se pudo encontrar la nota con ID: " + id);
    }
}
