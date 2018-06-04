package com.zlikun.docker.api;

import com.zlikun.docker.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
public class UserController {

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
        if (CollectionUtils.isEmpty(entries)) return null;
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
