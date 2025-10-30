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
    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

//    Show every created note
    public List<Note> allNotes() {
        return noteRepository.findAll();
    }

//    Show only the note specified by ID
    public Optional<Note> findNoteById(Long id){
        return noteRepository.findById(id);
    }

//    Delete the note
    public void deleteNote(Note note) {
        noteRepository.delete(note);
    }

//    The note exists
    public boolean existsById(Long id) {
        return noteRepository.existsById(id);
    }

//    Edit note
    public void updateNote(Long id, String content, String title) {
        noteRepository.findById(id).get().setContent(content);
        noteRepository.findById(id).get().setTitle(title);
    }
}
