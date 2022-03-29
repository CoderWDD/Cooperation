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
@TableName("uidpid")
public class UidPid implements Serializable {
    @TableField("user_id")
    private int userId;

    @TableField("project_id")
    private int projectId;
}
