package com.example.devFlow.project;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.devFlow.profile.Profile;
import com.example.devFlow.user.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByTitleContainingIgnoreCase(String title);

    List<Project> findByTitleContainingIgnoreCaseAndIsPrivate(String title, boolean isPrivate);

    List<Project> findByIsPrivate(boolean isPrivate);
    List<Project> findByUserId(Long userId);

    @Query("SELECT p FROM Project p " +
           "WHERE (:query IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%'))) " +
           "AND (:category IS NULL OR p.category = :category) " +
           "AND (:subcategory IS NULL OR p.subcategory = :subcategory)")
    List<Project> filterProjects(
        @Param("query") String query,
        @Param("category") ProjectCategory category,
        @Param("subcategory") ProjectSubcategory subcategory
    );

}
