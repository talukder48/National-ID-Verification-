package app.hm.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import app.hm.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
