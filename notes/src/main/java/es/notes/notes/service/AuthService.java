package es.notes.notes.service;

import es.notes.notes.exception.DuplicateUsernameException;
import es.notes.notes.model.AppUser;
import es.notes.notes.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUserRepository repo;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthService(AppUserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public void register(String username, String rawPassword) throws DuplicateUsernameException {
        if (repo.existsByUsername(username)) {
            throw new DuplicateUsernameException("The user already exists.");
        }
        AppUser u = new AppUser();
        u.setUsername(username);
        u.setPassword(encoder.encode(rawPassword));
        u.setRole("ROLE_USER");
        repo.save(u);
    }
}
