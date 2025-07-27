package org.nevesdev.comanda.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.nevesdev.comanda.dto.bar.BarCreate;
import org.nevesdev.comanda.model.bar.Bar;
import org.nevesdev.comanda.model.user.Role;

@Data
public class UserRegister {

    private String username;
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$",
            message = "A senha deve ter no mínimo 8 caracteres, incluindo letra maiúscula, minúscula, número e caractere especial"
    )
    private String passwd;
    private Role role;
    private BarCreate barCreate;

}
