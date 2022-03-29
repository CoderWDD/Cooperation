package com.example.cooperationproject.controller.itemController;

import com.example.cooperationproject.pojo.TaskItem;
import com.example.cooperationproject.service.ItemService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

// TODO : 加上权限判断，防止权限不够的也来删除

@RestController
public class ModifyItemInfoController {
    private final HttpServletRequest request;

    private final MyJwtUtil myJwtUtil;

    private final ItemService itemService;

    public ModifyItemInfoController(HttpServletRequest request, MyJwtUtil myJwtUtill, ItemService itemService) {
        this.request = request;
        this.myJwtUtil = myJwtUtill;
        this.itemService = itemService;
    }

    // TODO : 防止修改任务信息时，项目id也被改变
    // 解决方案：用一个不含projectId的实体类作为参数
    @PostMapping("/item/modifyItemInfo")
    public Message modifyItemInfo(@RequestBody TaskItem taskItem){
        String token = request.getHeader("token");
        String username = myJwtUtil.getUsernameFromToken(token);

        boolean isItemExciting = itemService.IsItemExciting(taskItem.getItemId(), taskItem.getItemName(), taskItem.getAuthor(), taskItem.getProjectId(), username);

        if (isItemExciting){
            // 如果任务存在，则可以修改
            boolean modifyItemInfo = itemService.ModifyItemInfo(taskItem);
            if (modifyItemInfo){
                return ResultUtil.success("任务信息修改成功！");
            }
        }
        return ResultUtil.error(StatusCode.BadRequest,"任务信息修改失败！");
    }
}
