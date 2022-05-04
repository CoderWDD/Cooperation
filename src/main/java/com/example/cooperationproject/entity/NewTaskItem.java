package com.example.cooperationproject.entity;

import com.example.cooperationproject.pojo.TaskItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class NewTaskItem {

    private String status;

    private String executor;

    private String itemName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date itemTime;

    private String description;
}
