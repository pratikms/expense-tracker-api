package com.pratikms.expensetrackerapi.services;

import java.util.regex.Pattern;

import com.pratikms.expensetrackerapi.domain.User;
import com.pratikms.expensetrackerapi.exceptions.AuthException;
import com.pratikms.expensetrackerapi.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws AuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");

        if (email != null) email = email.toLowerCase();
        if (!pattern.matcher(email).matches()) throw new AuthException("Invalid email format");
        Integer emailCount = userRepository.getCountByEmail(email);
        if (emailCount > 0) throw new AuthException("Email already in use");
        
        Integer userId = userRepository.create(firstName, lastName, email, password);
        
        return userRepository.findById(userId);
    }

    @Override
    public User validateUser(String email, String password) throws AuthException {
        // TODO Auto-generated method stub
        return null;
    }
    
}
