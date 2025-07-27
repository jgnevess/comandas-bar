package org.nevesdev.comanda.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.nevesdev.comanda.dto.user.UserLogin;
import org.nevesdev.comanda.dto.user.UserRegister;
import org.nevesdev.comanda.model.bar.Bar;
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
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private Role role;

    @OneToOne
    private Bar bar;

    public User(UserRegister userRegister) {
        this.username = userRegister.getUsername();
        this.password = userRegister.getPasswd();
        this.role = userRegister.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role.equals(Role.SUPER)) return List.of(new SimpleGrantedAuthority("ROLE_SUPER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
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
