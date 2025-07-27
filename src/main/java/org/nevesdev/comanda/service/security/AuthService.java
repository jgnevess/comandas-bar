package org.nevesdev.comanda.service.security;

import org.nevesdev.comanda.dto.user.UserRegister;
import org.nevesdev.comanda.dto.user.UserResponse;
import org.nevesdev.comanda.exceptions.UsernameException;
import org.nevesdev.comanda.model.bar.Bar;
import org.nevesdev.comanda.model.user.User;
import org.nevesdev.comanda.repository.BarRepository;
import org.nevesdev.comanda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BarRepository barRepository;

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
        Bar bar = new Bar(userRegister.getBarCreate().getBarName(), userRegister.getBarCreate().getAddress());
        if(!barRepository.existsByBarName(bar.getBarName())) {
            bar = barRepository.save(bar);
        }
        user.setBar(bar);
        user = userRepository.save(user);
        return new UserResponse(user.getUsername(), user.getRole());
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

}
