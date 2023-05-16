package com.gestionSalleCefim.Group3.services;
import com.gestionSalleCefim.Group3.entities.User;
import com.gestionSalleCefim.Group3.exceptions.EntityAlreadyExistsException;
import com.gestionSalleCefim.Group3.exceptions.InvalidEntityException;
import com.gestionSalleCefim.Group3.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User, UserRepository>{

    @Autowired
    private PasswordEncoder passwordEncoder;
// pour encoder le mdp au moment de l'ajout
    @Override
    public User save(User entity) throws EntityAlreadyExistsException, InvalidEntityException {
        String password = entity.getPassword();
        entity.setPassword(passwordEncoder.encode(password));
        return super.save(entity);
    }


}
