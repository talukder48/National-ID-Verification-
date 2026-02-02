package app.hm.ctl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.hm.entity.User;
import app.hm.enums.UserRole;
import app.hm.repo.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {

    @Autowired UserRepository userRepo;
    @Autowired PasswordEncoder passwordEncoder;

    /* ---------- INDEX ---------- */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /* ---------- LOGIN ---------- */
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            @RequestParam(value = "registered", required = false) String registered,
                            Model model) {

        if (error != null) model.addAttribute("error", "Invalid username or password");
        if (logout != null) model.addAttribute("msg", "Logged out successfully");
        if (registered != null) model.addAttribute("msg", "Registration successful! Please login");

        return "auth/login";
    }

    /* ---------- REGISTRATION FORM ---------- */
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    /* ---------- PROCESS REGISTRATION ---------- */
    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") @Valid User user,
                                  BindingResult bindingResult,
                                  Model model) {

        // Validate form
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        // Check if username exists
        if (userRepo.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Username already exists");
            return "auth/register";
        }


        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default role and enabled status
        user.setUserRole(UserRole.BIDDER);
        user.setEnabled(true);

        userRepo.save(user);

        return "redirect:/login?registered";
    }


   
}
