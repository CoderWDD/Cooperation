package com.example.cooperationproject.controller.itemController;


import com.example.cooperationproject.pojo.TaskItem;
import com.example.cooperationproject.service.ItemService;
import com.example.cooperationproject.service.TaskItemService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
public class GetItemListController {

    private final HttpServletRequest request;

    private final MyJwtUtil myJwtUtil;

    private final ItemService itemService;

    public GetItemListController(ItemService itemService, HttpServletRequest request, MyJwtUtil myJwtUtil) {
        this.itemService = itemService;
        this.request = request;
        this.myJwtUtil = myJwtUtil;
    }

    /**
     * 根据projectId获取相应的project下的所有item信息
     * @param projectId
     * @return
     */
    @ResponseBody
    @GetMapping("/item/getListByProjectId/{projectId}")
    public Message getItemListByProjectId(@PathVariable(value = "projectId") int projectId){

        List<TaskItem> taskItemList = itemService.GetItemListByProjectId(projectId);

        if (Objects.isNull(taskItemList)){
            // 如果获取不到信息
            return ResultUtil.error(StatusCode.BadRequest,"ProjectId出错！");
        }

        return ResultUtil.success(taskItemList);
    }

    /**
     * 获取当前账号下的所有item信息
     * @return
     */
    @ResponseBody
    @GetMapping("/item/getCurrentItemList")
    public Message getItemListByUsername(){

        String token = request.getHeader("token");
        String username = myJwtUtil.getUsernameFromToken(token);

        List<TaskItem> taskItemList = itemService.GetItemListByUsername(username);

        if (Objects.isNull(taskItemList)){
            // 如果获取不到信息
            return ResultUtil.error(StatusCode.NoContent,"当前用户没有任务！");
        }

        return ResultUtil.success(taskItemList);
    }

}
