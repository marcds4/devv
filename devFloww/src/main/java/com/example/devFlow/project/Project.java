package com.example.devFlow.project;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String description;

    private boolean isPrivate;

    private boolean showDevPrice;

    @Enumerated(EnumType.STRING)
    private ProjectCategory category;

    @Enumerated(EnumType.STRING)
    private ProjectSubcategory subcategory;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private Double maxPrice;

    @Enumerated(EnumType.STRING)
    private EstimatedDuration estimatedDuration;

    private int offerDurationDays;

    @ElementCollection
    private List<String> suggestedTechnologies;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
