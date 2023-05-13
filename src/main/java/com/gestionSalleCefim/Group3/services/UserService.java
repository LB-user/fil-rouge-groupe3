package com.gestionSalleCefim.Group3.services;

import com.gestionSalleCefim.Group3.entities.User;
import com.gestionSalleCefim.Group3.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User, UserRepository>{
}
