package es.notes.notes.controller;

import es.notes.notes.model.Note;
import es.notes.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/notes")
public class NoteController {

    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String getAllNotes(Model model) {
        model.addAttribute("notes", noteService.allNotes());
        return "index";
    }

    @GetMapping("/newNote")
    public String goToNewNote(Model model) {
        model.addAttribute("note", new Note());
        return "newNote";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute("note") Note note) {
        noteService.saveNote(note);
        return "redirect:/api/notes";
    }

    @GetMapping("/{id}")
    public String getNote(@PathVariable Long id, Model model) {
        Note note = noteService.findNoteById(id)
                .orElseThrow(() -> new RuntimeException("Note not found."));
        model.addAttribute("note", note);
        return "noteDesc";

    }

    @PostMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return "redirect:/api/notes";
    }

    @PostMapping("/{id}/update")
    public String updateNote(@PathVariable Long id, Model model, String title, String content, boolean completed) {
        model.addAttribute(noteService.updateNote(id, title, content, completed));
        return "redirect:/api/notes";
    }

//   TODO MAS COSAS YOKSE

}
