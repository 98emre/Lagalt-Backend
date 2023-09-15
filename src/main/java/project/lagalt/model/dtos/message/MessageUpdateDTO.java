package project.lagalt.model.dtos.message;

import lombok.Data;
import project.lagalt.utilites.enums.MessageStatus;

import java.time.LocalDateTime;


@Data
public class MessageUpdateDTO {

    private int id;
    private String title;
    private String text;
    private LocalDateTime date;
    private MessageStatus messageStatus;
    private Integer receiverId;


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


    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }
}
