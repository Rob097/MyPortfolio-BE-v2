package com.myprojects.myportfolio.auth.mapper;

public interface Mapper<T, Z> {

    default T map(Z input){
        return this.map(input, null);
    }

    T map(Z input, T output);

}
