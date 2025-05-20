package com.example.devFlow.profile;

import java.time.LocalDate;

import com.example.devFlow.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
@Entity
public class Profile {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id") // Foreign key column
    private User user;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dob;
    private String description;
    private String clientLink;
    private String profileImage;  // You can use this to store the file path or URL of the uploaded image

    // Getters and Setters
    public Profile() {
        this.profileImage = "/images/profile.jpg";
    }
    
    
    
    public Profile(Long id, User user, String firstName, String lastName, String gender, LocalDate dob,
			String description, String clientLink, String profileImage) {
		super();
		this.id = id;
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dob = dob;
		this.description = description;
		this.clientLink = clientLink;
		this.profileImage = profileImage;
	}



	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientLink() {
        return clientLink;
    }

    public void setClientLink(String clientLink) {
        this.clientLink = clientLink;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
