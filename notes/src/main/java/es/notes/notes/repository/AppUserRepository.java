package es.notes.notes.repository;

import es.notes.notes.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{
    boolean existsByUsername(String username);

    Object findByUsername(String username);
}
