package com.myprojects.myportfolio.clients.general;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.clients.general.views.Synthetic;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;

public interface IController <R, Q>{

    String VIEW = "view";

    ResponseEntity<MessageResources<R>> find(Q parameters, IView view) throws Exception;
    ResponseEntity<MessageResource<R>> get(Integer id, Q parameters, IView view) throws Exception;
    ResponseEntity<MessageResource<R>> create(R resource) throws Exception;
    ResponseEntity<MessageResource<R>> update(Integer id, R resource) throws Exception;
    ResponseEntity<MessageResource<R>> delete(Integer id) throws Exception;

}
