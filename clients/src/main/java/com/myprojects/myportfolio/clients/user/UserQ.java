package com.myprojects.myportfolio.clients.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserQ {
    private String key;
    private String operation;
    private Object value;
}
