package com.example.devFlow.comment;

public record CommentRequest(
    String username,
    String commentText,
    Long projectId
) {}