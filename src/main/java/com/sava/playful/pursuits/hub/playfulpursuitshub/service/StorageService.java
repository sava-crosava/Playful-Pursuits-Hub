package com.sava.playful.pursuits.hub.playfulpursuitshub.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;


@Service
@Slf4j
public class  StorageService {
    @Value("${application.bucket.name}")
    private String bucketName;
    @Autowired
    private AmazonS3 s3Client;


    public String uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        return fileName;
    }


    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void deleteFile(String fileName) {
        if(s3Client.doesObjectExist(bucketName, fileName)){
            s3Client.deleteObject(bucketName, fileName);
        }
        else {
            throw new RuntimeException("File " + fileName + " not found in bucket " + bucketName);
        }
    }


    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }



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

    private static class Range {
        private final long start;
        private final long end;
        private final long length;

        public Range(long start, long end, long totalLength) {
            this.start = start;
            this.end = end;
            this.length = Math.min(end - start + 1, totalLength);
        }

        public long getStart() {
            return start;
        }

        public long getEnd() {
            return end;
        }

        public long getLength() {
            return length;
        }
    }

}
