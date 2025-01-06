package com.kytoon.frameworks_project_backend.repository;

import com.kytoon.frameworks_project_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    User getUserById(Long userId);

    User findByUsername(String username);
}
