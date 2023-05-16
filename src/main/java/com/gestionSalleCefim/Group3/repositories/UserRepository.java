package com.gestionSalleCefim.Group3.repositories;

import com.gestionSalleCefim.Group3.entities.User;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Integer> {

Optional<User> findByEmail(String email);

}
