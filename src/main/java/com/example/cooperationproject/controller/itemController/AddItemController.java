package com.example.cooperationproject.controller.itemController;


import com.example.cooperationproject.constant.ConstantFiledUtil;
import com.example.cooperationproject.pojo.TaskItem;
import com.example.cooperationproject.pojo.UidPid;
import com.example.cooperationproject.pojo.UidTidAuid;
import com.example.cooperationproject.pojo.User;
import com.example.cooperationproject.service.ItemService;
import com.example.cooperationproject.service.UidPidService;
import com.example.cooperationproject.service.UidTidAuidService;
import com.example.cooperationproject.service.UserService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import com.mysql.cj.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
public class AddItemController {

    private final UidTidAuidService uidTidAuidService;

    private final HttpServletRequest request;

    private final MyJwtUtil myJwtUtil;

    private final ItemService itemService;

    private final UidPidService uidPidService;

    private final UserService userService;

    public AddItemController(HttpServletRequest request, MyJwtUtil myJwtUtil, ItemService itemService, UidPidService uidPidService, UidTidAuidService uidTidAuidService, UserService userService) {
        this.request = request;
        this.myJwtUtil = myJwtUtil;
        this.itemService = itemService;
        this.uidPidService = uidPidService;
        this.uidTidAuidService = uidTidAuidService;
        this.userService = userService;
    }

    @PostMapping("/item/add")
    public Message addItem(@RequestBody TaskItem taskItem){
        String token = request.getHeader("token");
        int userId = myJwtUtil.getUserIdFromToken(token);
        String username = myJwtUtil.getUsernameFromToken(token);

        // 设置任务的创建者为当前用户
        taskItem.setAuthor(username);

        int executorId = userId;

        if (StringUtils.isNullOrEmpty(taskItem.getExecutor())){
            // 如果没有执行人信息，则默认为自己
            taskItem.setExecutor(username);
        }else {
            User executor = userService.FindUserByUsername(taskItem.getExecutor());

            if (Objects.isNull(executor)){
                return ResultUtil.error(StatusCode.BadRequest,"执行人信息错误！");
            }
            executorId = executor.getUserId();
        }

        TaskItem item = itemService.FindItemByName(taskItem.getItemName(), taskItem.getAuthor(), taskItem.getProjectId());

        if (!Objects.isNull(item)){
            return ResultUtil.error(StatusCode.BadRequest,"任务信息已存在！");
        }

        List<UidPid> uidPidList = uidPidService.GetPidListByUid(userId);

        for (UidPid e : uidPidList){
            // 如果找到userId下有对应的projectId
            if (e.getProjectId() == taskItem.getProjectId()){

                boolean addItem = itemService.AddItem(taskItem);
                if (addItem){

                    // 将执行人和创建人的权限存入数据库

                    try{
                        UidTidAuid authorAu = new UidTidAuid(userId,taskItem.getItemId(), ConstantFiledUtil.AUTHOR_ID);

                        uidTidAuidService.InsertUidTidAuid(authorAu);

                        if (executorId != userId){

                            // 如果执行人和创建人不一样,默认执行人是管理员
                            UidTidAuid executorAu = new UidTidAuid(executorId, taskItem.getItemId(), ConstantFiledUtil.ADMIN_ID);

                            uidTidAuidService.InsertUidTidAuid(executorAu);
                        }

                    }catch (Exception exception){
                        return ResultUtil.error(StatusCode.BadRequest,"出错了！");
                    }

                    return ResultUtil.success("任务添加成功！");
                }
                return ResultUtil.error(StatusCode.BadRequest,"任务添加失败！");
            }
        }
        return ResultUtil.error(StatusCode.BadRequest,"项目不存在！");
    }

}
