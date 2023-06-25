package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.dto.RegistrationDto;
import net.javaguides.springboot.entity.Role;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.repository.RolesRespository;
import net.javaguides.springboot.repository.UserRespository;
import net.javaguides.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private UserRespository userRespository;
    private RolesRespository rolesRespository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRespository userRespository,
                           RolesRespository rolesRespository,
                           PasswordEncoder passwordEncoder) {
        this.userRespository = userRespository;
        this.rolesRespository = rolesRespository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        User user = new User();
        user.setName(registrationDto.getFirstName() + " " + registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        // use spring security to encrpty the password
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = rolesRespository.findByName("ROLE_GUEST");
        user.setRoles(Arrays.asList(role));
        this.userRespository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRespository.findByEmail(email);
    }
}
