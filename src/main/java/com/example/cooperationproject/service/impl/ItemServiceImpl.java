package com.example.cooperationproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cooperationproject.pojo.TaskItem;
import com.example.cooperationproject.mapper.TaskItemMapper;
import com.example.cooperationproject.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class ItemServiceImpl extends ServiceImpl<TaskItemMapper, TaskItem> implements ItemService {

    @Override
    public boolean AddItem(TaskItem taskItem) {
        boolean savaSuccessful = false;
        try {
            savaSuccessful = save(taskItem);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return savaSuccessful;
    }

    @Override
    public TaskItem FindItemById(Integer taskItemId) {
        QueryWrapper<TaskItem> wrapper = new QueryWrapper<>();
        wrapper.eq("item_id",taskItemId);

        TaskItem taskItem = getOne(wrapper);

        return taskItem;
    }

    @Override
    public TaskItem FindItemByName(String itemName, String author, Integer projectId) {
        QueryWrapper<TaskItem> wrapper = new QueryWrapper<>();

        wrapper.eq("item_name",itemName);
        wrapper.eq("author",author);
        wrapper.eq("project_id",projectId);

        TaskItem taskItem = getOne(wrapper);

        return taskItem;
    }

    /**
     * 判断任务是否已经存在
     * @param taskItemId
     * @param itemName
     * @param author
     * @param projectId
     * @param username
     * @return
     */
    @Override
    public boolean IsItemExciting(Integer taskItemId, String itemName, String author, Integer projectId,String username) {
        TaskItem findItemByIdRes = FindItemById(taskItemId);

        if (!Objects.isNull(findItemByIdRes)){
            // 如果任务id已经存在
            return true;
        }

        TaskItem findItemByNameRes = FindItemByName(itemName, username, projectId);

        if (!Objects.isNull(findItemByNameRes)){
            return true;
        }
        return false;
    }

    @Override
    public boolean DeleteItemById(Integer taskItemId) {

        boolean removeById = removeById(taskItemId);

        return removeById;
    }

    @Override
    public boolean DeleteItemByName(String itemName, String author, Integer projectId) {
        QueryWrapper<TaskItem> wrapper = new QueryWrapper<>();

        wrapper.eq("item_name",itemName);
        wrapper.eq("author",author);
        wrapper.eq("project_id",projectId);

        boolean remove = remove(wrapper);

        return remove;
    }

    @Override
    public boolean ModifyItemInfo(TaskItem taskItem) {
        boolean updateById = updateById(taskItem);

        return updateById;
    }

    @Override
    public List<TaskItem> GetItemListByProjectId(Integer projectId) {
        QueryWrapper<TaskItem> wrapper = new QueryWrapper<>();
        wrapper.eq("project_id",projectId);
        List<TaskItem> taskItemList = list(wrapper);
        return taskItemList;
    }

    @Override
    public List<TaskItem> GetItemListByUsername(String username) {
        QueryWrapper<TaskItem> wrapper = new QueryWrapper<>();
        wrapper.eq("author",username);
        List<TaskItem> taskItemList = list(wrapper);
        return taskItemList;
    }
}
