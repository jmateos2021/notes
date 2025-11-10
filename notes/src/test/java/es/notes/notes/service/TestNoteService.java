package es.notes.notes.service;

import es.notes.notes.model.AppUser;
import es.notes.notes.model.Note;
import es.notes.notes.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@ExtendWith(MockitoExtension.class)
public class TestNoteService {
    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    @WithMockUser(username = "test", roles = {"USER"})
    void mustReturnAllNotes() {
//        Arrange
        AppUser owner = new AppUser(1L,"jesus","1234","ROLE_USER");

        Note note1 = new Note(owner);
        note1.setCompleted(true);
        note1.setContent("Contenido nota 1");
        note1.setTitle("Nota 1");

        Note note2 = new Note(owner);
        note2.setCompleted(true);
        note2.setContent("Contenido nota 2");
        note2.setTitle("Nota 2");

        List<Note> simulatedList = Arrays.asList(note1, note2);

        when(noteRepository.findByOwnerUsername(owner.getUsername())).thenReturn(simulatedList);

//        .with(user("testUser").roles("USER"))
//                .with(csrf())

//        Act
        List<Note> result = noteService.allNotesFor(owner.getUsername());

//        Assert
        assertEquals(2, result.size());
        assertEquals("Contenido nota 1", result.get(0).getContent());
        assertEquals("Contenido nota 2", result.get(1).getContent());
    }
}