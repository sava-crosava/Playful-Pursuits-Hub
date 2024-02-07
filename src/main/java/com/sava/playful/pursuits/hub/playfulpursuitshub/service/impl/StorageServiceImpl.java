package com.sava.playful.pursuits.hub.playfulpursuitshub.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.sava.playful.pursuits.hub.playfulpursuitshub.service.StorageService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;


@Service
@Slf4j
public class  StorageServiceImpl implements StorageService {
    @Value("${application.bucket.name}")
    private String bucketName;
    private final AmazonS3 s3Client;

    private static final String PATH_TO_CHANNEL_ICONS = "channel-icons/";
    private static final String PATH_TO_THUMBNAILS = "thumbnails/";
    private static final String PATH_TO_VIDEOS = "videos/";
    private static final String PATH_TO_AVATARS = "avatars/";

    public StorageServiceImpl(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFile(MultipartFile file, String path) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, path + fileName, fileObj));
        fileObj.delete();
        return fileName;
    }
    @Override
    public String uploadThumbnail(MultipartFile thumbnail){
        return uploadFile(thumbnail, PATH_TO_THUMBNAILS);
    }
    @Override
    public String uploadVideo(MultipartFile video){
        return uploadFile(video, PATH_TO_VIDEOS);
    }

    @Override
    public String getFileUrl(String fileName, String path) {
        return s3Client.getUrl(bucketName,  path + fileName).toString();
    }

    @Override
    public String getThumbnailImageUrl(String thumbnailImageName) {
        return getFileUrl(thumbnailImageName, PATH_TO_THUMBNAILS);
    }

    @Override
    public String getChannelIconImageUrl(String channelIconImageName) {
        return getFileUrl(channelIconImageName, PATH_TO_CHANNEL_ICONS);
    }

    @Override
    public void deleteFile(String fileName) {
        if(s3Client.doesObjectExist(bucketName, fileName)){
            s3Client.deleteObject(bucketName, fileName);
        }
        else {
            throw new RuntimeException("File " + fileName + " not found in bucket " + bucketName);
        }
    }

    @Override
    public File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }


    @Override
    public ResponseEntity<StreamingResponseBody> getPartialObject(String fileName, String rangeHeader) {
        S3Object object = s3Client.getObject(bucketName, fileName);
        long fileSize = object.getObjectMetadata().getContentLength();
        Range range = parseRangeHeader(rangeHeader, fileSize);

        HttpHeaders responseHeaders = createResponseHeaders(range, fileSize);
        StreamingResponseBody body = createResponseBody(object, range);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .headers(responseHeaders)
                .body(body);
    }

    private Range parseRangeHeader(String rangeHeader, long fileSize) {
        if (rangeHeader == null || !rangeHeader.startsWith("bytes=")) {
            return new Range(0, fileSize - 1, fileSize);
        }

        String[] ranges = rangeHeader.substring(6).split("-");
        long start = Long.parseLong(ranges[0]);
        long end = ranges.length > 1 && !ranges[1].isEmpty() ? Long.parseLong(ranges[1]) : fileSize - 1;

        return new Range(start, end, fileSize);
    }

    private HttpHeaders createResponseHeaders(Range range, long fileSize) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range", "bytes " + range.getStart() + "-" + range.getEnd() + "/" + fileSize);
        return responseHeaders;
    }

    private StreamingResponseBody createResponseBody(S3Object object, Range range) {
        return outputStream -> {
            S3ObjectInputStream objectInputStream = object.getObjectContent();
            byte[] data = new byte[1024];
            long bytesToSend = range.getLength();
            objectInputStream.skip(range.getStart());

            int bytesRead;
            while (bytesToSend > 0 && (bytesRead = objectInputStream.read(data, 0, (int) Math.min(1024, bytesToSend))) != -1) {
                outputStream.write(data, 0, bytesRead);
                bytesToSend -= bytesRead;
            }

            objectInputStream.close();
        };
    }

    @Getter
    @AllArgsConstructor
    private static class Range {
        private final long start;
        private final long end;
        private final long length;
    }

}
