package com.example.devFlow.comment;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(length=1000)
    private String commentText;

    private Long projectId;

    @Column(name = "created_at", columnDefinition = "datetime")
    private Timestamp timestamp;

    public Comment() {
        this.timestamp = new java.sql.Timestamp(System.currentTimeMillis()); // σωστό initialization
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getCommentText() { return commentText; }
    public void setCommentText(String commentText) { this.commentText = commentText; }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Timestamp getCreatedAt() { return timestamp; }
    public void setCreatedAt(Timestamp createdAt) { this.timestamp = createdAt; }
}
