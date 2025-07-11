package org.nevesdev.comanda.dto.user;

import lombok.Data;
import org.nevesdev.comanda.model.user.Role;

@Data
public class UserRegister {

    private String username;
    private String passwd;
    private Role role;

}
