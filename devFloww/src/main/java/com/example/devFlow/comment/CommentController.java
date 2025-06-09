package com.example.devFlow.comment;

import com.example.devFlow.user.User;
import com.example.devFlow.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class CommentController {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentController(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/create_comment")
    public String showOfferForm(HttpSession session, Model model,@RequestParam("projectId") Long projectId) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        model.addAttribute("userId", userId);
        return "redirect:/projects";
    }

    @PostMapping("/create_comment")
    public String createComment(@RequestParam("projectId") Long projectId,
                                @RequestParam("commentText") String commentText,
                                HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        User user = userRepository.findById(userId).orElseThrow();

        Comment comment = new Comment();
        comment.setUsername(user.getUsername());
        comment.setProjectId(projectId);
        comment.setCommentText(commentText);
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        commentRepository.save(comment);

        return "redirect:/projects";
    }
}
