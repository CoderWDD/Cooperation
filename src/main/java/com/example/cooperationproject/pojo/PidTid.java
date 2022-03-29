package com.example.cooperationproject.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("pidtid")

public class PidTid implements Serializable {
    @TableField("project_id")
    private int projectId;

    @TableField("item_id")
    private int itemId;
}
