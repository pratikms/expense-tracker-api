package com.pratikms.expensetrackerapi.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;

import com.pratikms.expensetrackerapi.domain.User;
import com.pratikms.expensetrackerapi.exceptions.AuthException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_CREATE = "INSERT INTO users(id, first_name, last_name, email, password) VALUES (NEXTVAL('users_seq'), ?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM users WHERE email = ?";
    private static final String SQL_FIND_BY_ID = "SELECT id, first_name, last_name, email, password from users WHERE id = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT id, first_name, last_name, email, password FROM users WHERE email = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String firstName, String lastName, String email, String password) throws AuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, email);
                ps.setString(4, hashedPassword);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("id");
        } catch (Exception e) {
            throw new AuthException("Failed to create account");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws AuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
            if (!BCrypt.checkpw(password, user.getPassword())) throw new AuthException("Invalid password");
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new AuthException("Invalid email");
        }
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(
            rs.getInt("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"),
            rs.getString("password")
        );
    });
    
}
