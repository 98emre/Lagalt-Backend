package project.lagalt.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;



@Entity
@Table(name = "messageresponse")
public class MessageResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "responsetext" )
    private String responseText;

    @Column(name = "date")
    private LocalDate date;


    @ManyToOne
    @JoinColumn(name = "responder_id")
    private User responder;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

    public MessageResponse() {
    }

    public MessageResponse(String responseText, LocalDate date, User responder) {
        this.responseText = responseText;
        this.date = date;
        this.responder = responder;
    }

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

    public User getResponder() {
        return responder;
    }

    public void setResponder(User responder) {
        this.responder = responder;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
