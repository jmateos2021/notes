package es.notes.notes.controller;

import es.notes.notes.exception.NoteNotFoundException;
import es.notes.notes.model.Note;
import es.notes.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/notes")
public class NoteController {

    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String getAllNotes(Model model,
                              @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        model.addAttribute("notes", noteService.allNotesFor(authUser.getUsername()));
        return "index";
    }

    @GetMapping("/newNote")
    public String goToNewNote(Model model,
                              @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        model.addAttribute("note", new Note());
        return "newNote";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute("note") Note note,
                             @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        noteService.saveNoteFor(note, authUser.getUsername());
        return "redirect:/notes";
    }

    @GetMapping("/{id}")
    public String showNote(@PathVariable Long id, Model model,
                           @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        Note note = noteService.findNoteByIdFor(id, authUser.getUsername())
                .orElseThrow(() -> {
                    return new NoteNotFoundException(id);
                });
        model.addAttribute("note", note);
        return "noteDesc"; //Read only form
    }

    @DeleteMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id,
                             @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        noteService.deleteNoteByIdFor(id, authUser.getUsername());
        return "redirect:/notes";
    }

    @GetMapping("/{id}/edit")
    public String editNote(@PathVariable Long id, Model model,
                           @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        Note note = noteService.findNoteByIdFor(id, authUser.getUsername())
                .orElseThrow(() -> new NoteNotFoundException(id));
        model.addAttribute("note", note);
        return "noteEdit"; //Write form for editing and update note
    }

    @PutMapping("/{id}/update")
    public String updateNote(@PathVariable Long id, @ModelAttribute Note note,
                             @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        noteService.updateNoteFor(note, authUser.getUsername());
        return "redirect:/notes";
    }

}
