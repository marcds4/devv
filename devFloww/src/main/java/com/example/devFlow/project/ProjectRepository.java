package com.example.devFlow.project;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.devFlow.profile.Profile;
import com.example.devFlow.user.User;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByTitleContainingIgnoreCase(String title);
    List<Project> findByIsPrivate(boolean isPrivate);
    List<Project> findByTitleContainingIgnoreCaseAndIsPrivate(String title, boolean isPrivate);



}
