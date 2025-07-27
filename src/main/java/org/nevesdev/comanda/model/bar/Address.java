package org.nevesdev.comanda.model.bar;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Address {

    private String streetName;
    private String streetNumber;

}
