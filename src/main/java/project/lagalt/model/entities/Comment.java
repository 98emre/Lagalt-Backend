package project.lagalt.model.entities;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "text", length = 100)
    private String text;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time")
    private Date date;

    public Comment() {
    }

    public Comment(int id, String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
