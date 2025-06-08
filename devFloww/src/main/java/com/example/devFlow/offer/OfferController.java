package com.example.devFlow.offer;

import com.example.devFlow.user.User;
import com.example.devFlow.user.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Controller
public class OfferController {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    // Ο φάκελος που θα αποθηκεύονται τα αρχεία
    private final String UPLOAD_DIR = "uploads/";

    public OfferController(OfferRepository offerRepository, UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/create_offer")
public String showOfferForm(HttpSession session, Model model) {
    Long userId = (Long) session.getAttribute("userId");

    if (userId == null) {
        return "redirect:/login"; // or any fallback if not logged in
    }

    model.addAttribute("userId", userId); // send to Thymeleaf form if needed
    return "offer";
}


@PostMapping("/create_offer")
public String createOffer(HttpSession session,
                          @RequestParam("description") String description,
                          @RequestParam("file") MultipartFile file) {

    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
        return "redirect:/login"; // fallback if not logged in
    }

    User user = userRepository.findById(userId).orElseThrow();

    // Αποθήκευση του αρχείου
    String fileName = file.getOriginalFilename();
    try {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        file.transferTo(filePath.toFile());

    } catch (IOException e) {
        e.printStackTrace();
        return "error"; // Σελίδα σφάλματος
    }

    // Δημιουργία της προσφοράς
    Offer offer = new Offer();
    offer.setUser(user);
    offer.setDescription(description);
    offer.setFileName(fileName);

    offerRepository.save(offer);

    return "redirect:/client_dashboard?userId=" + userId;
}


}
