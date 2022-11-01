package com.myprojects.myportfolio.clients.general;

public interface Mapper<T, Z> {

    default T map(Z input){
        return this.map(input, null);
    }

    T map(Z input, T output);

}
