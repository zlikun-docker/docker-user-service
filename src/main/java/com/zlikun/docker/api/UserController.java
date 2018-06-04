package com.zlikun.docker.api;

import com.zlikun.docker.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping({ "/", "" })
    public Object index() {
        return "HELLO WORLD !";
    }

    @GetMapping("/{id}")
    public Object details(@PathVariable long id) {
        User user = this.getFromRedis(id);
        if (user != null) return user;
        return "USER_NOT_EXISTS";
    }

    private User getFromRedis(long id) {
        final String key = "user:" + id;
        BoundHashOperations<String, String, String> bho = stringRedisTemplate.boundHashOps(key);
        Map<String, String> entries = bho.entries();
        // 从数据库中查询数据
        if (CollectionUtils.isEmpty(entries)) {
            User user = this.getFromMySQL(id);
            if (user != null) {
                // 写入缓存
                bho.put("id", user.getId().toString());
                bho.put("nickname", user.getNickname());
                bho.put("mobile", user.getMobile());
                bho.put("status", user.getStatus().toString());
                bho.put("ctime", String.valueOf(user.getCtime().getTime()));
                bho.expire(24, TimeUnit.HOURS);
                // 不允许直接对外提供密码
                user.setPassword(null);
            }
            return user;
        } else {
            // 转换数据，并返回
            User user = new User();
            user.setId(NumberUtils.parseNumber(entries.get("id"), Long.class));
            user.setMobile(entries.get("mobile"));
            user.setNickname(entries.get("nickname"));
            user.setStatus(NumberUtils.parseNumber(entries.get("status"), Integer.class));
            user.setCtime(Date.from(Instant.ofEpochMilli(NumberUtils.parseNumber(entries.get("ctime"), Long.class))));
            return user;
        }
    }

    private User getFromMySQL(long id) {
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
