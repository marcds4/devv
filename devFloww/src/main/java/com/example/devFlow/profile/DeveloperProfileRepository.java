package com.example.devFlow.profile;

import com.example.devFlow.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeveloperProfileRepository extends JpaRepository<DeveloperProfile, Long> {
    Optional<DeveloperProfile> findByUser(User user);
    Optional<DeveloperProfile> findByUserUsername(String username);

}
