package com.example.cooperationproject.controller.itemController;

import com.example.cooperationproject.pojo.TaskItem;
import com.example.cooperationproject.service.ItemService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import com.mysql.cj.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class SetItemStatusController {
    private final HttpServletRequest request;

    private final MyJwtUtil myJwtUtil;

    private final ItemService itemService;

    public SetItemStatusController(HttpServletRequest request, MyJwtUtil myJwtUtil, ItemService itemService) {
        this.request = request;
        this.myJwtUtil = myJwtUtil;
        this.itemService = itemService;
    }

    // TODO : 加上权限判断，防止权限不够的也来修改

    @PostMapping("/item/setItemStatus/{itemId}")
    public Message SetItemStatus(@PathVariable Integer itemId, @RequestParam String itemStatus){
        String token = request.getHeader("token");
        String username = myJwtUtil.getUsernameFromToken(token);


        if (StringUtils.isNullOrEmpty(itemStatus)){
            return ResultUtil.error(StatusCode.BadRequest,"任务状态不能为空！");
        }

        TaskItem taskItem = itemService.FindItemById(itemId);

        if (!Objects.isNull(taskItem)){
            taskItem.setStatus(itemStatus);
            boolean modifyItemInfo = itemService.ModifyItemInfo(taskItem);

            if (modifyItemInfo){
                return ResultUtil.success("任务状态更新成功！");
            }
        }

        return ResultUtil.error(StatusCode.BadRequest,"任务状态更新失败！");
    }

}
