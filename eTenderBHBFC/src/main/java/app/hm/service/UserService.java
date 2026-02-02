package app.hm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.hm.entity.User;
import app.hm.enums.UserRole;
import app.hm.repo.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByUsername(email);
    }

    public boolean nidExists(String nid) {
        return userRepository.existsByNidNumber(nid);
    }

    public User registerBidder(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(UserRole.BIDDER);
        user.setEnabled(true);

        return userRepository.save(user);
    }
}
