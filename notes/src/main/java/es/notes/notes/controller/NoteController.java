package es.notes.notes.controller;

import es.notes.notes.model.Note;
import es.notes.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/api/notes")
public class NoteController {

    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute("note") Model model, Note note) {
        model.addAttribute("Note", noteService.saveNote(note));
        return "redirect:/api/notes";
    }

    @GetMapping("/")
    public String getAllNotes(Model model) {
        model.addAttribute("Notes", noteService.allNotes());
        return "index";
    }

    @GetMapping("/{id}")
    public String getNote(Long id, Model model) {
        Note note = noteService.findNoteById(id)
                .orElseThrow(() -> new RuntimeException("Note not found."));
        model.addAttribute("Note", note);
        return "notes";

    }

    @DeleteMapping
    public String deleteNote(Note note, Model model) {
        noteService.deleteNote(note);
        return "redirect:/api/notes";
    }

    @PutMapping
    public String updateNote(Long id, Model model, String title, String content) {
        model.addAttribute(noteService.updateNote(id, title, content));
        return "redirect:/api/notes";
    }

}
