package com.noriyuki.workshopmongo.repository;

import com.noriyuki.workshopmongo.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Transactional(readOnly = true)
    User findByEmail(String email);
}
