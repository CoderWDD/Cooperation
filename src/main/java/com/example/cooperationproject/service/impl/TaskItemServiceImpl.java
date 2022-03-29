package com.example.cooperationproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cooperationproject.mapper.TaskItemMapper;
import com.example.cooperationproject.pojo.TaskItem;
import com.example.cooperationproject.service.TaskItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskItemServiceImpl extends ServiceImpl<TaskItemMapper, TaskItem> implements TaskItemService {
    @Override
    public List<TaskItem> GetTaskItemListByUsername(String username) {
        QueryWrapper<TaskItem> wrapper = new QueryWrapper<>();
        wrapper.eq("executor",username);
        return list(wrapper);
    }
}
