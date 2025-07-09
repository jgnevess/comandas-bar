package org.nevesdev.comanda.repository;

import org.nevesdev.comanda.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
