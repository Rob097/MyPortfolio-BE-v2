package com.myprojects.myportfolio.core.controllers;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public class SimpleController<T> extends IController<T> {

    @Override
    protected ResponseEntity<MessageResources<T>> find(String filters, IView view, Pageable pageable) throws Exception {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    protected ResponseEntity<MessageResource<T>> get(Integer id, IView view) throws Exception {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    protected ResponseEntity<MessageResource<T>> create(T resource) throws Exception {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    protected ResponseEntity<MessageResource<T>> update(Integer id, T resource) throws Exception {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    protected ResponseEntity<MessageResource<T>> delete(Integer id) throws Exception {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
