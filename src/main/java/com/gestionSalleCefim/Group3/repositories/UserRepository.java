package com.gestionSalleCefim.Group3.repositories;

import com.gestionSalleCefim.Group3.entities.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends BaseRepository<User, Integer> {

    User findByEmail(String email);
}
