package com.pratikms.expensetrackerapi.repositories;

import com.pratikms.expensetrackerapi.domain.User;
import com.pratikms.expensetrackerapi.exceptions.AuthException;

public interface UserRepository {
    
    Integer create(String firstName, String lastName, String email, String password) throws AuthException;

    User findByEmailAndPassword(String email, String password) throws AuthException;

    Integer getCountByEmail(String email);

    User findById(Integer userId);
}
