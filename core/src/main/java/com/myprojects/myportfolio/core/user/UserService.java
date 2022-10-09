package com.myprojects.myportfolio.core.user;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        List<User> all = this.userRepository.findAll();
        return all;
    }

    public User findById(Integer id) {
        Validate.notNull(id, "Parametro obbligatorio mancante: id.");

        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new NoSuchElementException("Nessun utente trovato con id: " + id));
    }

    public User save(User u){
        Validate.notNull(u, "Parametro obbligatorio mancante: user.");

        if(u.getId()!=null) {
            Optional<User> actual = this.userRepository.findById(u.getId());
            Validate.isTrue(!actual.isPresent(), "Esiste già un utente applicativo registrato con ID " + u.getId());
        }

        User user = this.userRepository.save(u);
        return user;
    }

    public User update(User userToUpdate){
        Validate.notNull(userToUpdate, "Parametro obbligatorio mancante: user.");
        Validate.notNull(userToUpdate.getId(), "Parametro obbligatorio mancante: id user.");

        return this.userRepository.save(userToUpdate);
    }

}

