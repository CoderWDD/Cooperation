package com.example.cooperationproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cooperationproject.constant.ConstantFiledUtil;
import com.example.cooperationproject.entity.NewTaskItem;
import com.example.cooperationproject.pojo.TaskItem;
import com.example.cooperationproject.mapper.TaskItemMapper;
import com.example.cooperationproject.pojo.UidTidAuid;
import com.example.cooperationproject.pojo.User;
import com.example.cooperationproject.service.ItemService;
import com.example.cooperationproject.service.UidTidAuidService;
import com.example.cooperationproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class ItemServiceImpl extends ServiceImpl<TaskItemMapper, TaskItem> implements ItemService {

    private final UidTidAuidService uidTidAuidService;

    private final UserService userService;

    public ItemServiceImpl(UidTidAuidService uidTidAuidService, UserService userService) {
        this.uidTidAuidService = uidTidAuidService;
        this.userService = userService;
    }

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


//    @Override
//    public boolean IsItemExciting(Integer taskItemId, String itemName, String author, Integer projectId,String username) {
//        TaskItem findItemByIdRes = FindItemById(taskItemId);
//
//        if (!Objects.isNull(findItemByIdRes)){
//            // 如果任务id已经存在
//            return true;
//        }
//
//        TaskItem findItemByNameRes = FindItemByName(itemName, username, projectId);
//
//        if (!Objects.isNull(findItemByNameRes)){
//            return true;
//        }
//        return false;
//    }

    @Override
    public boolean DeleteItemById(Integer userId,Integer taskItemId) {

        boolean removeById = removeById(taskItemId);

        boolean uidtid = uidTidAuidService.DeleteByUIDTID(userId, taskItemId);

        return removeById && uidtid;
    }

    @Override
    public boolean ModifyItemInfo(String oldExecutor,Integer itemId,NewTaskItem newTaskItem) {

        TaskItem taskItem = FindItemById(itemId);

        if (Objects.isNull(taskItem)){
            return false;
        }

        // 如果执行人不一样了。需要更改其权限

        if (!taskItem.getExecutor().equals(newTaskItem.getExecutor())){

            User oldUser = userService.FindUserByUsername(oldExecutor);

            User newUser = userService.FindUserByUsername(newTaskItem.getExecutor());



            UidTidAuid uidTidAuid = new UidTidAuid(newUser.getUserId(),itemId, ConstantFiledUtil.ADMIN_ID);

            uidTidAuidService.InsertUidTidAuid(uidTidAuid);
            uidTidAuidService.DeleteByUIDTID(oldUser.getUserId(),itemId);
        }

        taskItem.setStatus(newTaskItem.getStatus());
        taskItem.setExecutor(newTaskItem.getExecutor());
        taskItem.setDescription(newTaskItem.getDescription());
        taskItem.setItemTime(newTaskItem.getItemTime());
        taskItem.setItemName(newTaskItem.getItemName());

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
