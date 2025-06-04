package com.example.devFlow.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findById(Long userId);
	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);

}
