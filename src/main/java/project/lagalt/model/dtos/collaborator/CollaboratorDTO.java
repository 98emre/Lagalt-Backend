package project.lagalt.model.dtos.collaborator;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Size;
import lombok.Data;
import project.lagalt.utilites.enums.Application;

@Data
public class CollaboratorDTO {

    private int id;
    private Application status;
    private LocalDateTime requestDate;
    private LocalDateTime approvalDate;

    @Size(max = 250, message = "Motivation Text is Max")
    private String motivation;
    private Integer userId;
    private Integer projectId;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
