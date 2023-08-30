package project.lagalt.model.dtos.comment;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private int id;
    private String text;
    private LocalDateTime date;

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
}
