package es.notes.notes.repository;

import es.notes.notes.model.AppUser;
import es.notes.notes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByOwnerUsername(String username);

    Optional<Note> findByIdAndOwnerUsername(Long id, String username);

    boolean existsByIdAndOwnerUsername(Long id, String username);
}
