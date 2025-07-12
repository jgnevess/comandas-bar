package org.nevesdev.comanda.repository;

import org.nevesdev.comanda.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);
    Boolean existsByUsername(String username);
}
