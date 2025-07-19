package org.nevesdev.comanda.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreate {
    @NotBlank(message = "O nome não pode ser vazio")
    private String clientName;
}
