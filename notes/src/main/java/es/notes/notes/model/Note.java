package es.notes.notes.model;

import jakarta.persistence.*;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private boolean completed;

    // Añado propietario nota. Relación muchos a uno, cada nota pertenece a un AppUser.
    // Lazy: Para carga perezosa...Al leer una nota, si a priori no necesitas el dueño, la bbdd solo te trae la nota.
    // Ventajas: rendimiento (menos datos si no necesitas el dueño).
    // Y por último mapeo la columna FK owner_id en la tala note. Si quisieras obligar a que haya dueño podemos: nullable=false
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private AppUser owner;

    public AppUser getOwner() {
        return owner;
    }

//  NO PONER SETTER A OWNER PARA QUE NO PUEDA CAMBIARSE UNA VEZ HECHA LA NOTA :D
//    public void setOwner(AppUser owner) {
//        this.owner = owner;
//    }

    public Note () {
    }

    public Note (AppUser owner) {
        this.owner = owner;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}