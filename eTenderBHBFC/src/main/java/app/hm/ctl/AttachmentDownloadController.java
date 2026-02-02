package app.hm.ctl;


import app.hm.entity.Attachment;
import app.hm.repo.AttachmentRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.FileInputStream;

@Controller
public class AttachmentDownloadController {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @GetMapping("/attachments/download/{id}")
    public ResponseEntity<InputStreamResource> downloadAttachment(
            @PathVariable("id") Long id
    ) throws Exception {

        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attachment not found"));

        File file = new File(attachment.getFilePath());
        if (!file.exists()) {
            throw new RuntimeException("File not found on server");
        }

        InputStreamResource resource =
                new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getName() + "\""
                )
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
