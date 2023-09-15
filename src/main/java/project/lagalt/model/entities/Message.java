package project.lagalt.model.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "read")
    private boolean read;


    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    private Set<MessageResponse> responses;

    public Message() {
    }

    public Message(String title, String text, LocalDateTime date, boolean read) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.read = read;
    }

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

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Set<MessageResponse> getResponses() {
        return responses;
    }

    public void setResponses(Set<MessageResponse> responses) {
        this.responses = responses;
    }

    @PrePersist
    @PreUpdate
    public void prePersistDate() {
        date = LocalDateTime.now();
    }
}
