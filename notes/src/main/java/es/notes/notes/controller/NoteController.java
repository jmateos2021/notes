package es.notes.notes.controller;

import es.notes.notes.exception.NoteNotFoundException;
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

    private Note findNoteByIdOrThrow(Long id) {
        return noteService.findNoteById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }

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
    public String showNote(@PathVariable Long id, Model model) {
        Note note = findNoteByIdOrThrow(id);
        model.addAttribute("note", note);
        return "noteDesc"; //Read only form
    }

    @DeleteMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return "redirect:/api/notes";
    }

    @GetMapping("/{id}/edit")
    public String editNote(@PathVariable Long id, Model model) {
        Note note = findNoteByIdOrThrow(id);
        model.addAttribute("note", note);
        return "noteEdit"; //Write form for editing and update note
    }

    @PutMapping("/{id}/update")
    public String updateNote(@PathVariable Long id, @ModelAttribute Note note) {
        noteService.updateNote(note);
        return "redirect:/api/notes";
    }

}
