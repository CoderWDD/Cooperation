package com.example.cooperationproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cooperationproject.pojo.TaskItem;

import java.util.List;

public interface TaskItemService extends IService<TaskItem> {
    List<TaskItem> GetTaskItemListByUsername(String username);

}
