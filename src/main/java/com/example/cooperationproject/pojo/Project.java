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
@TableName("project")

public class Project implements Serializable {
    @TableId
    @TableField("project_id")
    private int projectId;

    @TableField("status")
    private String status;

    @TableField("project_name")
    private String projectName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("project_time")
    private Date projectTime;

    @TableField("description")
    private String description;

    @TableField("invitation_code")
    private String invitationCode;

    @TableField("author")
    private String author;
}
