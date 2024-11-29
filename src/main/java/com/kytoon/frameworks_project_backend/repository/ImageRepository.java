package com.kytoon.frameworks_project_backend.repository;

import com.kytoon.frameworks_project_backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
