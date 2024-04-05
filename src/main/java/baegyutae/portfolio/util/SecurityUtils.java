package baegyutae.portfolio.util;

import baegyutae.portfolio.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static UserDetailsImpl getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) principal);
        } else {
            return null;
        }
    }

    public static Long getCurrentUserId() {
        UserDetailsImpl userDetails = getCurrentUserDetails();
        return userDetails != null ? userDetails.getUser().getId() : null;
    }
}
