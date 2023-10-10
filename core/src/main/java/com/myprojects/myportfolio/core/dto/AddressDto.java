package com.myprojects.myportfolio.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class AddressDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 5932608848302578678L;

    String nationality;
    String nation;
    String province;
    String city;
    String cap;
    String address;

}
