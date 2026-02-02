package app.hm.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.hm.dto.DocumentStorageProperties;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class DocumentService {

    private final Path documentStorageLocation;

    @Autowired
    public DocumentService(DocumentStorageProperties properties) {
        this.documentStorageLocation = Paths.get(properties.getUploadDir())
                .toAbsolutePath().normalize();
        
        try {
            Files.createDirectories(this.documentStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory for document storage.", ex);
        }
    }
    
    public String storeDocument(MultipartFile file, Long userId) throws IOException {
        // Validate file
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file");
        }
        
        // Check file size (max 2MB)
        if (file.getSize() > 2 * 1024 * 1024) {
            throw new IOException("File size exceeds 2MB limit");
        }
        
        // Check file type
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(fileName).toLowerCase();
        
        if (!isValidFileType(fileExtension)) {
            throw new IOException("Invalid file type. Only PDF, JPG, JPEG, PNG allowed");
        }
        
        // Generate unique filename
        String uniqueFileName = userId + "_" + UUID.randomUUID().toString() + "_" + fileName;
        
        try {
            // Copy file to the target location
            Path targetLocation = this.documentStorageLocation.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            return uniqueFileName;
            
        } catch (IOException ex) {
            throw new IOException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    
    public Resource loadDocument(String fileName) throws IOException {
        try {
            Path filePath = this.documentStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists()) {
                return resource;
            } else {
                throw new IOException("File not found: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new IOException("File not found: " + fileName, ex);
        }
    }
    
    public void deleteDocument(String fileName) throws IOException {
        try {
            Path filePath = this.documentStorageLocation.resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            throw new IOException("Could not delete file: " + fileName, ex);
        }
    }
    
    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? "" : fileName.substring(lastDotIndex + 1);
    }
    
    private boolean isValidFileType(String fileExtension) {
        return fileExtension.equals("pdf") || 
               fileExtension.equals("jpg") || 
               fileExtension.equals("jpeg") || 
               fileExtension.equals("png");
    }
}

