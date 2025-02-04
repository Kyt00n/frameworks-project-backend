package com.kytoon.frameworks_project_backend.repository;

import com.kytoon.frameworks_project_backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByContainerId(Long postId);
}
