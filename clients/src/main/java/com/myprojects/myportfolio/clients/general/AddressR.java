package com.myprojects.myportfolio.clients.general;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddressR {
    private String nation;
    private String province;
    private String city;
    private String cap;
    private String address;
}
