package com.example.devFlow.project;

import java.util.List;

public record ProjectRequest(String title,String description,boolean isPrivate,boolean showDevPrice,
		ProjectCategory category,ProjectSubcategory subcategory,PaymentMethod paymentMethod,Double maxPrice,
		EstimatedDuration estimatedDuration,int offerDurationDays,List<String> suggestedTechnologies) {

}