package com.zlikun.docker.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class User {

    private Long id;
    private String nickname;
    private String mobile;
    @JsonIgnore
    private String password;
    private Date ctime;
    private Integer status;

}
