package com.kytoon.frameworks_project_backend.repository;

import com.kytoon.frameworks_project_backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
