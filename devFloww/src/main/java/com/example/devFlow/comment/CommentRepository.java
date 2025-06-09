package com.example.devFlow.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByProjectIdOrderByTimestampAsc(Long projectId);
    List<Comment> findByProjectId(Long projectId);
    
}