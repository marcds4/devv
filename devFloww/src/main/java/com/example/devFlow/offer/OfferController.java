package com.example.devFlow.offer;

import com.example.devFlow.user.User;
import com.example.devFlow.user.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Controller
public class OfferController {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    public OfferController(OfferRepository offerRepository, UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/create_offer")
    public String showOfferForm(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        model.addAttribute("userId", userId);
        return "offer";
    }

    @PostMapping("/create_offer")
    public String createOffer(HttpSession session,
                              @RequestParam("description") String description,
                              @RequestParam("file") MultipartFile file) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        User user = userRepository.findById(userId).orElseThrow();

        if (file != null && !file.isEmpty()) {
            // Sanitize the filename to avoid path traversal issues
            String fileName = Paths.get(file.getOriginalFilename()).getFileName().toString();

            try {
                // Get current working directory dynamically
                String currentDir = System.getProperty("user.dir");
                Path uploadPath = Paths.get(currentDir, "devflow_uploads");

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                file.transferTo(filePath.toFile());

                Offer offer = new Offer();
                offer.setUser(user);
                offer.setDescription(description);
                offer.setFileName(fileName);

                offerRepository.save(offer);

            } catch (IOException e) {
                e.printStackTrace();
                return "error"; // Show error page on failure
            }
        } else {
            // Handle case when no file is uploaded
            Offer offer = new Offer();
            offer.setUser(user);
            offer.setDescription(description);
            offer.setFileName(null);

            offerRepository.save(offer);
        }

        return "redirect:/client_dashboard?userId=" + userId;
    }
}
