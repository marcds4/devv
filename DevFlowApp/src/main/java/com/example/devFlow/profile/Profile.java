package com.example.devFlow.profile;

import java.time.LocalDate;

import com.example.devFlow.user.User;

import jakarta.persistence.*;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dob;
    private String description;
    private String clientLink;
    private String profileImage;

    public Profile() {}

    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getClientLink() { return clientLink; }
    public void setClientLink(String clientLink) { this.clientLink = clientLink; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
}
