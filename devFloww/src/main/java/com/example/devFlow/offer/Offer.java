package com.example.devFlow.offer;

import com.example.devFlow.user.User;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Συσχέτιση με τον χρήστη που έκανε την προσφορά
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String description;

    // Όνομα αρχείου που ανέβηκε (π.χ. PDF)
    private String fileName;

    @Column(name = "created_at", columnDefinition = "datetime")
    private Timestamp createdAt;

    public Offer() {
        this.createdAt = new Timestamp(System.currentTimeMillis()); // σωστό initialization
    }

    // --- Getters & Setters ---

    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
