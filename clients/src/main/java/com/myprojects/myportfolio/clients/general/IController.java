package com.myprojects.myportfolio.clients.general;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import org.springframework.http.ResponseEntity;

public interface IController <R, Q>{

    String VIEW = "view";

    ResponseEntity<MessageResources<R>> find(Q parameters) throws Exception;
    ResponseEntity<MessageResource<R>> get(Integer id, Q parameters) throws Exception;
    ResponseEntity<MessageResource<R>> create(R resource) throws Exception;
    ResponseEntity<MessageResource<R>> update(Integer id, R resource) throws Exception;
    ResponseEntity<MessageResource<R>> delete(Integer id) throws Exception;

}
