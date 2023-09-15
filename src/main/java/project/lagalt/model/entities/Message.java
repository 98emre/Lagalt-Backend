package project.lagalt.model.entities;

import jakarta.persistence.*;
import project.lagalt.utilites.enums.MessageStatus;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "text",nullable = false)
    private String text;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "read")
    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    public Message() {
    }

    public Message(String title, String text, LocalDateTime date, MessageStatus messageStatus) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.messageStatus = messageStatus;
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

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
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

    @PrePersist
    @PreUpdate
    public void prePersistDate() {
        date = LocalDateTime.now();
    }
}
