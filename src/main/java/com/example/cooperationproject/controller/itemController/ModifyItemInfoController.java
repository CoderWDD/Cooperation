package com.example.cooperationproject.controller.itemController;

import com.example.cooperationproject.constant.ConstantFiledUtil;
import com.example.cooperationproject.entity.NewTaskItem;
import com.example.cooperationproject.pojo.TaskItem;
import com.example.cooperationproject.service.ItemService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


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

    @ResponseBody
    @PostMapping("/item/modify/{itemId}")
    public Message modifyItemInfo(@PathVariable(value = "itemId") Integer itemId, @RequestBody NewTaskItem newTaskItem){
        String token = request.getHeader("token");
        String username = myJwtUtil.getUsernameFromToken(token);

        TaskItem taskItem = itemService.FindItemById(itemId);
        if (Objects.isNull(taskItem)){
            return ResultUtil.error(StatusCode.BadRequest,"ItemId错误！");
        }

        boolean modifyItemInfo = itemService.ModifyItemInfo(taskItem.getExecutor(),itemId, newTaskItem);

        if (modifyItemInfo){
            return ResultUtil.success("修改成功！");
        }

        return ResultUtil.error(StatusCode.BadRequest,"任务信息修改失败！");
    }
}
