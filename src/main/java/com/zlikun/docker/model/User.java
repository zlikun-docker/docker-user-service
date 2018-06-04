package com.zlikun.docker.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    private Long id;
    private String nickname;
    private String mobile;
    private String password;
    private LocalDateTime ctime;
    private Integer status;

}
