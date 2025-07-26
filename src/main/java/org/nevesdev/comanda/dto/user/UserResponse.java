package org.nevesdev.comanda.dto.user;

import org.nevesdev.comanda.model.user.Role;

public record UserResponse(String username, Role role) {
}
