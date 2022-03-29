package com.example.cooperationproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cooperationproject.pojo.TaskItem;

import java.util.List;

public interface ItemService extends IService<TaskItem> {
    boolean AddItem(TaskItem taskItem);

    TaskItem FindItemById(Integer taskItemId);

    TaskItem FindItemByName(String itemName,String author,Integer projectId);

    boolean IsItemExciting(Integer taskItemId,String itemName,String author,Integer projectId,String username);

    boolean DeleteItemById(Integer taskItemId);

    boolean DeleteItemByName(String itemName,String author,Integer projectId);

    boolean ModifyItemInfo(TaskItem taskItem);

    List<TaskItem> GetItemListByProjectId(Integer projectId);


    List<TaskItem> GetItemListByUsername(String username);



}
