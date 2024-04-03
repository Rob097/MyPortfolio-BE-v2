package com.myprojects.myportfolio.clients.general;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SetUpRequest {

    private String nation;
    private String region;
    private String city;
    private String cap;
    private String address;
    private String type;
    private String role;
    private String bio;
    private List<Integer> skills;

}
