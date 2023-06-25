package net.javaguides.springboot.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecurityUtils {

    // Getting a Current log in user
    public static User getCurrentuser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }
        return null;
    }

    // Getting a Current log in user role/authority
    public static String getRole() {
        User user = getCurrentuser();
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            return authority.getAuthority();
        }
        return null;
    }
}
