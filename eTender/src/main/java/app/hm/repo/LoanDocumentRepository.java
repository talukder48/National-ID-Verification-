package app.hm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.hm.entity.LoanApplication;
import app.hm.entity.LoanDocument;

public interface LoanDocumentRepository extends JpaRepository<LoanDocument, Long> {
	 List<LoanDocument>findByLoanApplication(LoanApplication loan);
}
