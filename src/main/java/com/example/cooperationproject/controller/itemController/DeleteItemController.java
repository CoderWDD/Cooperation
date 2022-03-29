package com.example.cooperationproject.controller.itemController;


import com.example.cooperationproject.constant.AuthenticationConstant;
import com.example.cooperationproject.pojo.TaskItem;
import com.example.cooperationproject.pojo.UidTidAuid;
import com.example.cooperationproject.service.ItemService;
import com.example.cooperationproject.service.UidTidAuidService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import com.mysql.cj.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class DeleteItemController {

    private final UidTidAuidService uidTidAuidService;

    private final HttpServletRequest request;

    private final MyJwtUtil myJwtUtil;

    private final ItemService itemService;

    public DeleteItemController(HttpServletRequest request, MyJwtUtil myJwtUtil, ItemService itemService, UidTidAuidService uidTidAuidService) {
        this.request = request;
        this.myJwtUtil = myJwtUtil;
        this.itemService = itemService;
        this.uidTidAuidService = uidTidAuidService;
    }

    @PostMapping("/item/deleteItem")
    public Message deleteItem(@RequestBody TaskItem taskItem){
        String token = request.getHeader("token");
        String username = myJwtUtil.getUsernameFromToken(token);
        int userId = myJwtUtil.getUserIdFromToken(token);

        if (StringUtils.isNullOrEmpty(taskItem.getAuthor())){
            taskItem.setAuthor(username);
        }

        boolean isItemExciting = itemService.IsItemExciting(taskItem.getItemId(), taskItem.getItemName(), taskItem.getAuthor(), taskItem.getProjectId(), username);

        if (!isItemExciting){
            return ResultUtil.error(StatusCode.BadRequest,"任务信息不存在！");
        }

        UidTidAuid uidTidAuid = uidTidAuidService.FindAuidByUidTid(userId, taskItem.getItemId());

        if (Objects.isNull(uidTidAuid) || uidTidAuid.getAuId() == AuthenticationConstant.USER){
            // 没有权限或者是user权限都不能删除item
            return ResultUtil.error(StatusCode.Forbidden,"没有权限！");
        }

        boolean deleteItemById = itemService.DeleteItemById(taskItem.getItemId());

        if (deleteItemById){
            return ResultUtil.success("任务删除成功！");
        }

        boolean deleteItemByName = itemService.DeleteItemByName(taskItem.getItemName(), taskItem.getAuthor(), taskItem.getProjectId());

        if (deleteItemByName){
            return ResultUtil.success("任务删除成功！");
        }

        return ResultUtil.error(StatusCode.BadRequest,"任务删除失败！");
    }
}
