package project.lagalt.model.entities;


import jakarta.persistence.*;
import project.lagalt.utilites.enums.Application;

import java.time.LocalDateTime;

@Entity
@Table(name = "collaborator")
public class Collaborator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "status")
    private Application status;

    @Column(name = "request_date")
    private LocalDateTime requestDate;

    @Column(name = "approval_date")
    private LocalDateTime approvalDate;

    @Column(name="motivation", length = 200, nullable = false)
    private String motivation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Collaborator() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Application getStatus() {
        return status;
    }

    public void setStatus(Application status) {
        this.status = status;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    @PrePersist
    @PreUpdate
    public void updateDate() {
        if (status == Application.PENDING) {
            requestDate = LocalDateTime.now();
        }

        if (status == Application.APPROVED && approvalDate == null) {
            approvalDate = LocalDateTime.now();
            requestDate = null;
        } else if (status == Application.PENDING || status == Application.DECLINED) {
            approvalDate = null;
        }
    }
}

