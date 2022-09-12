package com.myprojects.myportfolio.clients.general;

public interface mapper<T, Z> {

    T map(Z input);

    T map(Z input, T output);

}
