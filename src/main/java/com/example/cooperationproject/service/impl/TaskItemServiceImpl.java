package com.example.cooperationproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cooperationproject.mapper.TaskItemMapper;
import com.example.cooperationproject.pojo.Project;
import com.example.cooperationproject.pojo.TaskItem;
import com.example.cooperationproject.service.ProjectService;
import com.example.cooperationproject.service.TaskItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskItemServiceImpl extends ServiceImpl<TaskItemMapper, TaskItem> implements TaskItemService {

    /**
     * 获取为executor、creator角色的itemList
     * @param username
     * @return
     */
    @Override
    public List<TaskItem> GetTaskItemListByUsername(String username) {
        QueryWrapper<TaskItem> wrapper = new QueryWrapper<>();
        wrapper.eq("executor",username).or().eq("author",username);
        return list(wrapper);
    }

    @Override
    public List<TaskItem> GetTaskItemListByProjectId(Integer projectId) {
        QueryWrapper<TaskItem> wrapper = new QueryWrapper<>();

        wrapper.eq("project_id",projectId);

        return list(wrapper);
    }
}
