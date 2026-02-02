package app.hm.config;


import app.hm.entity.User;
import app.hm.enums.UserRole;
import app.hm.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataLoader {
	@Autowired PasswordEncoder passwordEncoder;
    @Bean
    CommandLineRunner initDefaultApprovers(UserRepository userRepository) {
        return args -> {
            // Check if approvers already exist
            List<User> existingApprovers = userRepository.findByUserRole(UserRole.ADMIN);
            if (existingApprovers.isEmpty()) {
                // Pre-load default approvers
                User approver1 = new User();
                approver1.setName("Golam Mostofa");
                approver1.setUsername("golam.mostofa@bhbfc.gov.bd");
                approver1.setPassword(passwordEncoder.encode("Bhbfc@123")); // {noop} for plain text (use encoder in prod)
                approver1.setPhone("01710000001");
                approver1.setUserRole(UserRole.ADMIN);
                approver1.setEnabled(true);
                approver1.setCreatedAt(LocalDate.now());

                User approver2 = new User();
                approver2.setName("MD ABDUL MANNAN");
                approver2.setUsername("md@bhbfc.gov.bd");
                approver2.setPassword(passwordEncoder.encode("MD@123"));
                approver2.setPhone("01710000002");
                approver2.setUserRole(UserRole.ADMIN);
                approver2.setEnabled(true);
                approver2.setCreatedAt(LocalDate.now());

                // Save to DB
                userRepository.saveAll(List.of(approver1, approver2));

                System.out.println("✅ Default tender approvers loaded into DB.");
            } else {
                System.out.println("ℹ️ Tender approvers already exist in DB.");
            }
        };
    }
}
