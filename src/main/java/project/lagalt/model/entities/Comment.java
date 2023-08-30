package project.lagalt.model.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "text", length = 100)
    private String text;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Comment() {
    }

    public Comment(int id, String text, LocalDateTime date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Project getProject() {
        return project;
    }

    public void setProjects(Project project) {
        this.project = project;
    }

    @PrePersist
    @PreUpdate
    public void prePersistDate() {
        date = LocalDateTime.now();
    }
}
