package com.example.devFlow.comment;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getCommentsByProjectId(Long projectId) {
        return commentRepository.findByProjectIdOrderByTimestampAsc(projectId);
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }
}