package com.lagoinha.connect.repositories;

import com.lagoinha.connect.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends MongoRepository<User, String> {
    UserDetails findByLogin(String login);

    User findByLoginOrderByName(String login);

}
