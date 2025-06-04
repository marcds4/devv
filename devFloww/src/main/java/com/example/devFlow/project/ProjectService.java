package com.example.devFlow.project;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.devFlow.profile.Profile;
import com.example.devFlow.profile.ProfileRequest;

import java.util.List;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository repo;

    public ProjectService(ProjectRepository repo) {
        this.repo = repo;
    }

    public Project saveProject(ProjectRequest request) {
    	Project project = new Project();
    	project.setTitle(request.title());
    	project.setDescription(request.description());
    	project.setPrivate(request.isPrivate());
    	project.setShowDevPrice(request.showDevPrice());
        project.setCategory(request.category());
        project.setSubcategory(request.subcategory());
        project.setPaymentMethod(request.paymentMethod());
        project.setMaxPrice(request.maxPrice());
        project.setEstimatedDuration(request.estimatedDuration());
        project.setOfferDurationDays(request.offerDurationDays());
        project.setSuggestedTechnologies(request.suggestedTechnologies());
        return repo.save(project);
    }

    public List<Project> getAllProjects() {
        return repo.findAll();
    }
}
