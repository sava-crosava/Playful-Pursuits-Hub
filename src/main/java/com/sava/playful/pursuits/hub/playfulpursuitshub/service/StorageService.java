package com.sava.playful.pursuits.hub.playfulpursuitshub.service;

import com.amazonaws.services.s3.model.S3Object;
import com.sava.playful.pursuits.hub.playfulpursuitshub.service.impl.StorageServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;

public interface StorageService {
    String uploadFile(MultipartFile file);

    byte[] downloadFile(String fileName);

    void deleteFile(String fileName);

    File convertMultiPartFileToFile(MultipartFile file);

    ResponseEntity<StreamingResponseBody> getPartialObject(String fileName, String rangeHeader);
//TODO range clas!
//    StorageServiceImpl.Range parseRangeHeader(String rangeHeader, long fileSize);
//
//    HttpHeaders createResponseHeaders(StorageServiceImpl.Range range, long fileSize);
//
//    StreamingResponseBody createResponseBody(S3Object object, StorageServiceImpl.Range range);
}
