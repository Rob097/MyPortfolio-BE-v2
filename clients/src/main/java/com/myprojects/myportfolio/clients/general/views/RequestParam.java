package com.myprojects.myportfolio.clients.general.views;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation per abilitare un custom binding dei parametri della request all'interno di un model.
 * Di default spring mappa il nome del parametro sul campo con lo stesso nome.
 * Questa annotation permette di specificare il nome del parametro sulla request che dovr� essere
 * mappato per il campo che riporta l'annotation.
 * 
 * Da non confondersi con {@link org.springframework.web.bind.annotation.RequestParam @RequestParam} che 
 * viene usata per gli argomenti dei metodi di un {@link org.springframework.web.bind.annotation.RestController @RestController}
 * 
 * @author u08446
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {

    /**
     * The name of the request parameter to bind to.
     */
    String value();

}