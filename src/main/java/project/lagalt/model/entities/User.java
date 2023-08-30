package project.lagalt.model.entities;


import jakarta.persistence.*;
import project.lagalt.utilites.enums.Skills;

import java.util.Set;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "username", length = 50, nullable = false,unique = true)
    private String username;

    @Column(name = "email", length = 50,unique = true)
    private String email;

    @Column(name = "fullname", length = 50)
    private String fullname;

    @Column(name = "skills")
    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "user_skills", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Skills> skills;

    @ManyToMany(mappedBy = "users")
    private Set<Project> projects;

    @OneToMany(mappedBy = "user")
    private Set<Collaborator> collaborators;

    public User(){

    }

    public User(int id, String username,String email, String fullname, Set<Skills> skills) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.skills = skills;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Set<Skills> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skills> skills) {
        this.skills = skills;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(Set<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", skills=" + skills +
                '}';
    }
}
