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
@TableName("uidtidauid")
public class UidTidAuid implements Serializable {
    @TableField("user_id")
    private int userId;

    @TableField("item_id")
    private int itemId;

    @TableField("au_id")
    private int auId;
}
