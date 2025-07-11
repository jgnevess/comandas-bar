package org.nevesdev.comanda.service.security;

import org.nevesdev.comanda.dto.user.UserLogin;
import org.nevesdev.comanda.dto.user.UserRegister;
import org.nevesdev.comanda.model.user.User;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User createUser(UserRegister userRegister) {
        userRegister.setPasswd(new BCryptPasswordEncoder().encode(userRegister.getPasswd()));
        User user = new User(userRegister);
        return userRepository.save(user);
    }
}
