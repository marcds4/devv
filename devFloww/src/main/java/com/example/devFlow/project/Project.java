package com.example.devFlow.project;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.example.devFlow.user.User;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String title;

    @Column(length = 5000)
    private String description;

    private boolean isPrivate;

    private boolean showDevPrice;

    @Enumerated(EnumType.STRING)
    private ProjectCategory category;

    @Enumerated(EnumType.STRING)
    private ProjectSubcategory subcategory;
	
@Transient
private String formattedDatePosted;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
	@Column(nullable = false, updatable = false)
	private LocalDateTime datePosted;
    private Double maxPrice;
	
public String getFormattedDatePosted() {
    return formattedDatePosted;
}

public void setFormattedDatePosted(String formattedDatePosted) {
    this.formattedDatePosted = formattedDatePosted;
}
	@PrePersist
	protected void onCreate() {
		this.datePosted = java.time.LocalDateTime.now();
	}

	
	public LocalDateTime getDatePosted() {
		return datePosted;
	}

    @Enumerated(EnumType.STRING)
    private EstimatedDuration estimatedDuration;

    private int offerDurationDays;

    @ElementCollection
    private List<String> suggestedTechnologies;
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public boolean isShowDevPrice() {
		return showDevPrice;
	}

	public void setShowDevPrice(boolean showDevPrice) {
		this.showDevPrice = showDevPrice;
	}

	public ProjectCategory getCategory() {
		return category;
	}

	public void setCategory(ProjectCategory category) {
		this.category = category;
	}

	public ProjectSubcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(ProjectSubcategory subcategory) {
		this.subcategory = subcategory;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public EstimatedDuration getEstimatedDuration() {
		return estimatedDuration;
	}

	public void setEstimatedDuration(EstimatedDuration estimatedDuration) {
		this.estimatedDuration = estimatedDuration;
	}

	public int getOfferDurationDays() {
		return offerDurationDays;
	}

	public void setOfferDurationDays(int offerDurationDays) {
		this.offerDurationDays = offerDurationDays;
	}

	public List<String> getSuggestedTechnologies() {
		return suggestedTechnologies;
	}

	public void setSuggestedTechnologies(List<String> suggestedTechnologies) {
		this.suggestedTechnologies = suggestedTechnologies;
	}
    
    
}
