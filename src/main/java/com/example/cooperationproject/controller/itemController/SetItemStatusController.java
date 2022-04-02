package com.example.cooperationproject.controller.itemController;

import com.example.cooperationproject.entity.NewTaskItem;
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

    /**
     * 根据itemId修改item的status
     * @param itemId
     * @param itemStatus
     * @return
     */
    @ResponseBody
    @PostMapping("/item/setStatus/{itemId}")
    public Message SetItemStatus(@PathVariable(value = "itemId") Integer itemId, @RequestParam String itemStatus){
        String token = request.getHeader("token");
        String username = myJwtUtil.getUsernameFromToken(token);


        if (StringUtils.isNullOrEmpty(itemStatus)){
            return ResultUtil.error(StatusCode.BadRequest,"任务状态不能为空！");
        }

        TaskItem taskItem = itemService.FindItemById(itemId);

        if (!Objects.isNull(taskItem)){
            NewTaskItem newTaskItem = new NewTaskItem(taskItem);
            newTaskItem.setStatus(itemStatus);
            boolean modifyItemInfo = itemService.ModifyItemInfo(taskItem.getExecutor(),taskItem.getItemId(),newTaskItem);

            if (modifyItemInfo){
                return ResultUtil.success("任务状态更新成功！");
            }
        }else {
            return ResultUtil.error(StatusCode.BadRequest,"ItemId错误！");
        }

        return ResultUtil.error(StatusCode.BadRequest,"任务状态更新失败！");
    }

}
