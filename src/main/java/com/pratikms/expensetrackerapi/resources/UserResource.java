package com.pratikms.expensetrackerapi.resources;

import java.util.HashMap;
import java.util.Map;

import com.pratikms.expensetrackerapi.domain.User;
import com.pratikms.expensetrackerapi.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        
        User user = userService.validateUser(email, password);
        
        Map<String, String> map = new HashMap<>();
        map.put("message", "Logged in successfully");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap) {
        String firstName = (String) userMap.get("firstname");
        String lastName = (String) userMap.get("lastname");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        
        User user = userService.registerUser(firstName, lastName, email, password);
        
        Map<String, String> map = new HashMap<>();
        map.put("message", "Registered successfully");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
}
