package project.lagalt.model.entities;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "collaborator")
public class Collaborator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "status")
    private boolean status;

    @Column(name = "request_date")
    private LocalDateTime requestDate;

    @Column(name = "approval_date")
    private LocalDateTime approvalDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Collaborator() {
    }

    public Collaborator(int id, boolean status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    @PrePersist
    @PreUpdate
    public void updateDate() {
        if (requestDate == null) {
            requestDate = LocalDateTime.now();
        }

        if (status && approvalDate == null) {
            approvalDate = LocalDateTime.now();
            requestDate = null;
        } else if (!status) {
            approvalDate = null;
        }
    }
}

