package com.pratikms.expensetrackerapi.services;

import com.pratikms.expensetrackerapi.domain.User;
import com.pratikms.expensetrackerapi.exceptions.AuthException;

public interface UserService {
    
    User validateUser(String email, String password) throws AuthException;

    User registerUser(String firstName, String lastName, String email, String password) throws AuthException;
}
