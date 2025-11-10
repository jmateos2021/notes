package es.notes.notes.controller;

import es.notes.notes.service.NoteService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
public class NoteControllerTest {

//    Inyecta el simulador http para enviar peticiones
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;

    @Test
    public void saveNoteForTest() throws Exception {

//        Note note = new Note();
//        note.setId(1L);
//        note.setTitle("Note1");
//        note.setContent("This is the first note");

//        AppUser user = new AppUser();
//        user.setUsername("testUser");
//        user.setId(1L);

        doNothing().when(noteService).saveNoteFor(argThat(note -> "Note1".equals(note.getTitle()) && "This is the first note".equals(note.getContent()) && !note.isCompleted()), eq("testUser"));

        mockMvc.perform(post("/notes/create")
                        .with(user("testUser").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "Note1")
                        .param("content", "This is the first note")
                        .param("completed", "false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/notes"));

        verify(noteService).saveNoteFor(argThat(note -> "Note1".equals(note.getTitle()) && "This is the first note".equals(note.getContent()) && !note.isCompleted()), eq("testUser"));
}
}
