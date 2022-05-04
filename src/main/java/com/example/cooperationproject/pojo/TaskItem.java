package com.example.cooperationproject.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("taskitem")

public class TaskItem implements Serializable {
    @TableId(type = IdType.AUTO)
    private int itemId;

    /**
     * 项目id不能为null，否则放不进数据库
     */
    @NonNull
    @TableField("project_id")
    private int projectId;

    @TableField("status")
    private String status;

    @TableField("author")
    private String author;

    @TableField("executor")
    private String executor;

    @TableField("item_name")
    private String itemName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("item_time")
    private Date itemTime;

    @TableField("description")
    private String description;

    @TableField("project_name")
    private String projectName;

}
