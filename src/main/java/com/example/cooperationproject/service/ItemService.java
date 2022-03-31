package com.example.cooperationproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cooperationproject.entity.NewTaskItem;
import com.example.cooperationproject.pojo.TaskItem;

import java.util.List;

public interface ItemService extends IService<TaskItem> {
    boolean AddItem(TaskItem taskItem);

    TaskItem FindItemById(Integer taskItemId);

    TaskItem FindItemByName(String itemName,String author,Integer projectId);

    boolean DeleteItemById(Integer userId,Integer taskItemId);

    boolean ModifyItemInfo(Integer itemId,NewTaskItem newTaskItem);

    List<TaskItem> GetItemListByProjectId(Integer projectId);


    List<TaskItem> GetItemListByUsername(String username);



}
