package project.lagalt.model.entities;


import jakarta.persistence.*;
import project.lagalt.utilites.enums.Category;
import project.lagalt.utilites.enums.Status;

import java.util.Set;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private int id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "description", length = 1000, nullable = false)
    private String descriptions;

    @Column(name = "gitlink", length = 1000, nullable = false)
    private String gitlink;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    @JoinTable(name = "project_user", joinColumns = {
            @JoinColumn(name = "project_id")}, inverseJoinColumns = {
            @JoinColumn(name = "user_id")
    })
    private Set<User> users;

    public Project() {

    }

    public Project(int id, String title, String descriptions, String gitlink, Category category, Status status) {
        this.id = id;
        this.title = title;
        this.descriptions = descriptions;
        this.gitlink = gitlink;
        this.category = category;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getGitlink() {
        return gitlink;
    }

    public void setGitlink(String gitlink) {
        this.gitlink = gitlink;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + descriptions + '\'' +
                ", gitLink='" + gitlink + '\'' +
                ", category=" + category +
                ", status=" + status +
                '}';
    }
}
