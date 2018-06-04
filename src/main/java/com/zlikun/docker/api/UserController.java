package com.zlikun.docker.api;

import com.zlikun.docker.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping({ "/", "" })
    public Object index() {
        return "HELLO WORLD !";
    }

    @GetMapping("/{id}")
    public Object details(@PathVariable long id) {
        User user = this.get(id);
        if (user != null) return user;
        return "USER_NOT_EXISTS";
    }

    private User get(long id) {
        try {
            return jdbcTemplate.queryForObject("select * from user where id = ?",
                    new BeanPropertyRowMapper<>(User.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            log.error("主键查询用户信息失败!", e);
            throw e;
        }
    }

}
