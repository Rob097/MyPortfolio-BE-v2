package com.myprojects.myportfolio.clients.general;

public interface Mapper<T, Z> {

    T map(Z input);

    T map(Z input, T output);

}
