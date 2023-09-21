package com.myprojects.myportfolio.clients.general.views;

import org.apache.logging.log4j.util.Strings;
import org.springframework.core.convert.converter.Converter;

public class CustomStringToIViewConverter implements Converter<String, IView> {
    @Override
    public IView convert(String view) {
        if (Strings.isBlank(view)) {
            return Normal.value;
        }

        if (view.equals(Verbose.name)) {
            return Verbose.value;
        } else {
            return Normal.value;
        }

    }
}
