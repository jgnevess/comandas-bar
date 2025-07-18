package org.nevesdev.comanda.model.user;

import jakarta.persistence.*;
import lombok.*;
import org.nevesdev.comanda.dto.user.UserLogin;
import org.nevesdev.comanda.dto.user.UserRegister;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "user_tb")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private Role role;

    public User(UserRegister userRegister) {
        this.username = userRegister.getUsername();
        this.password = userRegister.getPasswd();
        this.role = userRegister.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role.equals(Role.ADMIN)) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return List.of(new SimpleGrantedAuthority("ROLE_SELLER"));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
