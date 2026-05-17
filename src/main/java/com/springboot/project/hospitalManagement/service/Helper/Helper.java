package com.springboot.project.hospitalManagement.service.Helper;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.project.hospitalManagement.dto.ApiResponse;

import java.nio.file.*;
import java.util.UUID;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Helper {

    private final String uploadDir = "uploads/";
    private final JavaMailSender mailSender;

    public String uploadFile(MultipartFile file) {

        validateImage(file);

        try {
            String fileName = generateFileName(file.getOriginalFilename());

            Path path = Paths.get(uploadDir, fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            return fileName;

        } catch (Exception e) {
            throw new RuntimeException("File upload failed", e);
        }
    }

    public Resource getFile(String fileName) {

        try {
            Path path = Paths.get(uploadDir, fileName);

            if (!Files.exists(path)) {
                throw new RuntimeException("File not found: " + fileName);
            }

            return new UrlResource(path.toUri());

        } catch (Exception e) {
            throw new RuntimeException("Error fetching file", e);
        }
    }

    public void deleteFile(String fileName) {

        if (fileName == null || fileName.isEmpty())
            return;

        try {
            Path path = Paths.get(uploadDir, fileName);
            Files.deleteIfExists(path);

        } catch (Exception e) {
            throw new RuntimeException("File delete failed", e);
        }
    }

    public String updateFile(String oldFileName, MultipartFile newFile) {

        validateImage(newFile);

        deleteFile(oldFileName);

        return uploadFile(newFile);
    }

    // =========================
    // 🔥 COMMON REUSABLE METHODS
    // =========================

    // ✅ Generate Unique File Name
    public String generateFileName(String originalName) {
        return UUID.randomUUID() + "_" + originalName;
    }

    // ✅ Validate File Type (Image Only)
    public void validateImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        String contentType = file.getContentType();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Only image files are allowed");
        }
    }

    public Resource loadFileAsResource(String fileName) {
        return getFile(fileName);
    }

    public ResponseEntity<Resource> buildFileResponse(Resource file) {
        try {
            String contentType = Files.probeContentType(file.getFile().toPath());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE,
                            contentType != null ? contentType : "application/octet-stream")
                    .body(file);

        } catch (Exception e) {
            throw new RuntimeException("Error building file response", e);
        }
    }

    public String buildFileUrl(String fileName) {

        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/patients/image/")
                .path(fileName)
                .toUriString();
    }

    // =========================
    // RESPONSE METHODS
    // =========================

    public <T> ResponseEntity<ApiResponse<T>> success(
            T data,
            String message,
            int status) {
        return ResponseEntity.status(status).body(
                ApiResponse.<T>builder()
                        .status(true)
                        .message(message)
                        .data(data)
                        .build());
    }

    public <T> ResponseEntity<ApiResponse<T>> successWithPagination(
            T data,
            String message,
            int status,
            Object pagination) {
        return ResponseEntity.status(status).body(
                ApiResponse.<T>builder()
                        .status(true)
                        .message(message)
                        .data(data)
                        .pagination(pagination)
                        .build());
    }

    public <T> ResponseEntity<ApiResponse<T>> failure(
            String message,
            int status,
            T data) {
        return ResponseEntity.status(status).body(
                ApiResponse.<T>builder()
                        .status(false)
                        .message(message)
                        .data(data)
                        .build());
    }

    public void sendOtpMail(
            String toEmail,
            String name,
            String otp) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);

            helper.setSubject(
                    "Hospital Management - OTP Verification");

            String html = """
                    <div style="
                        font-family: Arial;
                        padding: 20px;
                        background: #f4f4f4;
                    ">

                        <div style="
                            max-width: 600px;
                            margin: auto;
                            background: white;
                            padding: 30px;
                            border-radius: 10px;
                        ">

                            <h2 style="color:#2c3e50;">
                                Hospital Management System
                            </h2>

                            <p>Hello %s,</p>

                            <p>
                                Your OTP verification code is:
                            </p>

                            <div style="
                                font-size: 32px;
                                font-weight: bold;
                                color: #27ae60;
                                margin: 20px 0;
                            ">
                                %s
                            </div>

                            <p>
                                This OTP is valid for 5 minutes.
                            </p>

                            <p>
                                If you did not request this,
                                please ignore this email.
                            </p>

                            <br>

                            <p>
                                Regards,<br>
                                Hospital Management Team
                            </p>

                        </div>

                    </div>
                    """.formatted(name, otp);

            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to send OTP email");
        }
    }

}