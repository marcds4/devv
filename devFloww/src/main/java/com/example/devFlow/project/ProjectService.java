package com.example.devFlow.project;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectService {
	
@Autowired
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    public Project createProject(ProjectRequest request) {
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
        return projectRepository.save(project);
    }

}