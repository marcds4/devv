package com.example.devFlow.project;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.devFlow.profile.Profile;
import com.example.devFlow.user.User;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
