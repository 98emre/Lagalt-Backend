package project.lagalt.model.dtos.collaborator;

import lombok.Data;
import project.lagalt.utilites.enums.Application;
import project.lagalt.utilites.enums.Status;

import java.time.LocalDateTime;

@Data
public class CollaboratorDTO {

    private int id;
    private Application status;
    private LocalDateTime requestDate;
    private LocalDateTime approvalDate;
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
