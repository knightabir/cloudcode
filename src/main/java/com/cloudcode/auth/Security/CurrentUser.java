package com.cloudcode.auth.Security;

import com.cloudcode.auth.Model.User;
import com.cloudcode.auth.repository.UserRepository;
import com.cloudcode.company.model.Company;
import com.cloudcode.company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CurrentUser {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public User getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == "anonymousUser") {
                throw new RuntimeException("User is not authenticated");
            }

            Object principal = authentication.getPrincipal();
            String email;

            if (principal instanceof UserDetails) {
                email = ((UserDetails) principal).getUsername();
            } else {
                email = principal.toString();
            }

            User user = userRepository.findUserByEmail(email);

            if (user == null) {
                throw new RuntimeException("User not found");
            }

            return user;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public Company getCurrentUserCompany() {
        try {
            User user = getCurrentUser();
            return companyRepository.findByUserId(user.getId()).orElseThrow(() -> new RuntimeException("Company not found"));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
