package app.hm.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//Configuration class for document storage
@Component
@ConfigurationProperties(prefix = "document")
public class DocumentStorageProperties {
 private String uploadDir = "uploads/documents";
 
 public String getUploadDir() {
     return uploadDir;
 }
 
 public void setUploadDir(String uploadDir) {
     this.uploadDir = uploadDir;
 }
}