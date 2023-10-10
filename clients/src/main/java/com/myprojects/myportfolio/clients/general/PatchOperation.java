package com.myprojects.myportfolio.clients.general;

import lombok.Getter;

/**
 * Classe modello per le operazioni PATCH secondo lo standard <a href="https://tools.ietf.org/html/rfc6902">RFC 6902</a>
 */
@Getter
public class PatchOperation {
    public enum Op {

        add,
        remove,
        replace,
        move,
        copy,
        test
    }

    /**
     * -- GETTER --
     *  L'operazione da effettuare
     *
     */
    private Op op;
    /**
     * -- GETTER --
     *  Il path (json pointer secondo lo standard <a href="https://tools.ietf.org/html/rfc6901">RFC 6901</a>) su cui applicare l'operazione.
     *
     */
    private String path;

    /**
     * -- GETTER --
     *  Il valore da utilizzare per l'operazione richiesta
     *
     */
    private String value;
    /**
     * -- GETTER --
     *  Il path (json pointer secondo lo standard <a href="https://tools.ietf.org/html/rfc6901">RFC 6901</a>) da copiare/spostare
     *  in caso di operazioni di tipo 
     *  o 
     *
     */
    private String from;

    public void setOp(Op op) {
        this.op = op;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
