package es.notes.notes.service;

import es.notes.notes.model.AppUser;
import es.notes.notes.model.Note;
import es.notes.notes.repository.AppUserRepository;
import es.notes.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    //Create repository attribute for the service to connect with it
    private final NoteRepository noteRepository;
    private final AppUserRepository userRepository;

    //    @Autowired
    public NoteService(NoteRepository noteRepository, AppUserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    //    Create a note
    public void saveNoteFor(Note note, String username) {
        AppUser owner = userRepository.findByUsername(username).orElseThrow();

        Note noteToSave = new Note(owner);
        noteToSave.setTitle(note.getTitle());
        noteToSave.setContent(note.getContent());
        noteToSave.setCompleted(note.isCompleted());

        noteRepository.save(noteToSave);
    }

    //    Show every created note
    public List<Note> allNotesFor(String username) {
        return noteRepository.findByOwnerUsername(username);
    }

    //    Show only the note specified by ID
    public Optional<Note> findNoteByIdFor(Long id, String username) {
        return noteRepository.findByIdAndOwnerUsername(id, username);
    }

    //    Delete the note
    public void deleteNoteByIdFor(Long id, String username) {
        Note n = noteRepository.findByIdAndOwnerUsername(id, username).orElseThrow();
        noteRepository.delete(n);
    }

    //    The note exists
    public boolean existsByIdFor(Long id, String username) {
        return noteRepository.existsByIdAndOwnerUsername(id, username);
    }

    //    Update note
    public Note updateNoteFor(Note note, String username) {
        Note n = noteRepository.findByIdAndOwnerUsername(note.getId(), username).orElseThrow();
        n.setTitle(note.getTitle());
        n.setContent(note.getContent());
        n.setCompleted(note.isCompleted());

        return noteRepository.save(n);
    }
}
