package net.javaguides.springboot.security;

import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRespository userRespository;

    @Autowired
    public CustomUserDetailsService(UserRespository userRespository) {
        this.userRespository = userRespository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRespository.findByEmail(email);
        if(user != null){
            org.springframework.security.core.userdetails.User authenticatedUser =
                    new org.springframework.security.core.userdetails.User(
                            user.getEmail(),
                            user.getPassword(),
                            user.getRoles().stream()
                                    .map((role) -> new SimpleGrantedAuthority(role.getName()))
                                    .collect(Collectors.toList())
                    );
            return authenticatedUser;
        }else{
            throw new UsernameNotFoundException("Invalid username and password");
        }
    }
}
