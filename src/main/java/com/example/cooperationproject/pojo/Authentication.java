package com.example.cooperationproject.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("authentication")

public class Authentication implements Serializable {

    @TableId
    @TableField("au_id")
    private int auId;

    @TableField("an_name")
    private String anName;

}
