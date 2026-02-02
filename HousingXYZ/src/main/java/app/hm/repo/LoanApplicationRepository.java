package app.hm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.hm.entity.LoanApplication;
import app.hm.entity.User;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
	 // Find all loan applications by a specific user
    List<LoanApplication> findByUser(User user);
}
