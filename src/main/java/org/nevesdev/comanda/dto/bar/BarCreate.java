package org.nevesdev.comanda.dto.bar;

import jakarta.persistence.Embedded;
import lombok.Data;
import org.nevesdev.comanda.model.bar.Address;

@Data
public class BarCreate {

    private String barName;
    @Embedded
    private Address address;
}
