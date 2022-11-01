package com.myprojects.myportfolio.clients.general.views;

import org.apache.logging.log4j.util.Strings;
import org.springframework.core.convert.converter.Converter;

public class CustomStringToIViewConverter implements Converter<String, IView> {
    @Override
    public IView convert(String view) {
        if(Strings.isBlank(view)){
            return Synthetic.value;
        }

        switch (view){
            case UltraSynthetic.name:
                return UltraSynthetic.value;
            case Synthetic.name:
            default:
                return Synthetic.value;
            case Normal.name:
                return Normal.value;
            case Verbose.name:
                return Verbose.value;
        }
    }
}
