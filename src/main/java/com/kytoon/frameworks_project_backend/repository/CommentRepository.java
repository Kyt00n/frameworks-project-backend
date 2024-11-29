package com.kytoon.frameworks_project_backend.repository;

import com.kytoon.frameworks_project_backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
