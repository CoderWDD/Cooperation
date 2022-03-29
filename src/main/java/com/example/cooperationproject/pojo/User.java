package com.example.cooperationproject.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User implements Serializable {

    @TableId
    @TableField("user_id")
    private Integer userId;

    @TableField("user_name")
    private String userName;

    @TableField("nick_name")
    private String nickName;

    @TableField("password")
    private String password;

    @TableField("phone")
    private String phone;

    @TableField("first_name")
    private String firstName;

    @TableField("last_name")
    private String lastName;


    @TableField("department")
    private String department;

    @TableField("description")
    private String description;

    @TableField("avatar")
    private byte[] avatar;

    @TableField("delete_flag")
    private Integer deleteFlag;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("create_time")
    private Date createTime;

    @TableField("sex")
    private Character sex;
}
