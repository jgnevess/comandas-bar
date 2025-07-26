package org.nevesdev.comanda.service.security;

import jakarta.validation.Valid;
import org.nevesdev.comanda.dto.user.UserLogin;
import org.nevesdev.comanda.dto.user.UserRegister;
import org.nevesdev.comanda.dto.user.UserResponse;
import org.nevesdev.comanda.exceptions.UsernameException;
import org.nevesdev.comanda.model.user.Role;
import org.nevesdev.comanda.model.user.User;
import org.nevesdev.comanda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public UserResponse createUser(UserRegister userRegister) {
        if(userRegister.getPasswd().isBlank() || userRegister.getUsername().isBlank()) {
            throw new UsernameException("Username cannot be empty", 409);
        }
        if(userRepository.existsByUsername(userRegister.getUsername())) {
            throw new UsernameException("Username already exists", 409);
        }
        userRegister.setPasswd(encryptPassword(userRegister.getPasswd()));
        User user = new User(userRegister);
        user = userRepository.save(user);
        return new UserResponse(user.getUsername(), user.getRole());
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

}
