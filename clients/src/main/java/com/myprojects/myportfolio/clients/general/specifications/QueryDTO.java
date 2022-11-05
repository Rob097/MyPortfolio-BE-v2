package com.myprojects.myportfolio.clients.general.specifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryDTO {
    private String key;
    private String operation;
    private Object value;
}
