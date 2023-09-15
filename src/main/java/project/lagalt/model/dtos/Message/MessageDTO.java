package project.lagalt.model.dtos.Message;


import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class MessageDTO {


    private int id;
    private String title;
    private String text;
    private LocalDateTime date;
    private boolean read;

    private Integer senderId;
    private Integer receiverId;
    private Set<Integer> responseIds;

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

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public Set<Integer> getResponseIds() {
        return responseIds;
    }

    public void setResponseIds(Set<Integer> responseIds) {
        this.responseIds = responseIds;
    }
}
