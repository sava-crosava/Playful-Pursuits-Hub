package com.sava.playful.pursuits.hub.playfulpursuitshub.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;

public interface StorageService {
    String uploadFile(MultipartFile file, String path);

    String uploadThumbnail(MultipartFile thumbnail);
    String uploadVideo(MultipartFile video);

//    byte[] downloadFile(String fileName, String path);

    String getFileUrl(String fileName, String path);

    String getThumbnailImageUrl(String thumbnailImageName);

    String getChannelIconImageUrl(String channelIconImageName);

    void deleteFile(String fileName);

    File convertMultiPartFileToFile(MultipartFile file);

    ResponseEntity<StreamingResponseBody> getPartialObject(String fileName, String rangeHeader);

}
