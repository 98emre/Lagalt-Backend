package project.lagalt.model.dtos.messageresponse;

import lombok.Data;


import java.time.LocalDate;

@Data
public class MessageResponseDTO {

    private int id;
    private String responseText;
    private LocalDate date;
    private Integer responderId;
    private Integer messageId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getResponderId() {
        return responderId;
    }

    public void setResponderId(Integer responderId) {
        this.responderId = responderId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
}
