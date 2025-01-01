package com.kytoon.frameworks_project_backend.service.image;

import com.kytoon.frameworks_project_backend.dto.ImageDto;
import com.kytoon.frameworks_project_backend.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> file, Long postId);
    void updateImage(MultipartFile file, Long imageId);
}
