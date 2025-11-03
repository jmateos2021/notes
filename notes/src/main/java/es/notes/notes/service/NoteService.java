package es.notes.notes.service;

import es.notes.notes.model.Note;
import es.notes.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    //Create repository attribute for the service to connect with it
    private NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    //    Create a note
    public void saveNote(Note note) {
        noteRepository.save(note);
    }

    //    Show every created note
    public List<Note> allNotes() {
        return noteRepository.findAll();
    }

    //    Show only the note specified by ID
    public Optional<Note> findNoteById(Long id) {
        return Optional.of(noteRepository.findById(id).get());
    }

    //    Delete the note
    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }

    //    The note exists
    public boolean existsById(Long id) {
        return noteRepository.existsById(id);
    }

    //    Update note
    public Note updateNote(Note note) {
        Optional<Note> optionalNote = noteRepository.findById(note.getId());

        Note existingNote = optionalNote.get();
        existingNote.setTitle(note.getTitle());
        existingNote.setContent(note.getContent());
        existingNote.setCompleted(note.isCompleted());

        return noteRepository.save(existingNote);
    }
}
