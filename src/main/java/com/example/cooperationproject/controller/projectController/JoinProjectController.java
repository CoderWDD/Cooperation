package com.example.cooperationproject.controller.projectController;

import com.example.cooperationproject.constant.ConstantFiledUtil;
import com.example.cooperationproject.pojo.*;
import com.example.cooperationproject.service.*;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class JoinProjectController {

    private final TaskItemService taskItemService;

    private final UidTidAuidService uidTidAuidService;

    private final UidPidAuidService uidPidAuidService;

    private final UidPidService uidPidService;

    private final ProjectService projectService;

    private final HttpServletRequest request;

    private final MyJwtUtil myJwtUtil;

    private final UserService userService;

    public JoinProjectController(UidPidService uidPidService, ProjectService projectService, HttpServletRequest request, MyJwtUtil myJwtUtil, UidPidAuidService uidPidAuidService, UidTidAuidService uidTidAuidService, TaskItemService taskItemService, UserService userService) {
        this.uidPidService = uidPidService;
        this.projectService = projectService;
        this.request = request;
        this.myJwtUtil = myJwtUtil;
        this.uidPidAuidService = uidPidAuidService;
        this.uidTidAuidService = uidTidAuidService;
        this.taskItemService = taskItemService;
        this.userService = userService;
    }

    /**
     * 根据invitationCode加入对应的project
     * @param invitationCode
     * @return
     */
    @ResponseBody
    @PostMapping("/project/joinProject/{invitationCode}")
    public Message joinProject(@PathVariable(value = "invitationCode") String invitationCode){
        Project project = projectService.FindProjectByInvitationCode(invitationCode);
        String token = request.getHeader("token");
        String username = myJwtUtil.getUsernameFromToken(token);

        if (Objects.isNull(project)){
            return ResultUtil.error(StatusCode.BadRequest,"邀请码出错！");
        }

        if (username.equals(project.getAuthor())){
            return ResultUtil.error(StatusCode.BadRequest,"视图加入自己的项目！");
        }

        // 获取project的cooperators，并封装成list

        List<UidPid> uidPidList = uidPidService.GetUidListByPid(project.getProjectId());

        List<String> cooperators = new ArrayList<>();

        for (UidPid temp : uidPidList){
            String username_1 = userService.FindUsernameByUserId(temp.getUserId());
            // 只获取合作伙伴，不获取当前用户
            if (!Objects.isNull(username_1) && !username_1.isEmpty()) {
                cooperators.add(username_1);
            }
        }

        if (cooperators.contains(username)) {
            return ResultUtil.error(StatusCode.BadRequest,"你已经是该项目的成员！");
        }

        int userId = myJwtUtil.getUserIdFromToken(token);

        boolean insert = uidPidService.InsertByUidPid(userId, project.getProjectId());

        if (!insert) {
            return ResultUtil.error(StatusCode.BadRequest,"加入失败！");
        }

        // 被邀请进了项目，还需要将其权限写入数据库

        // 默认被邀请进去都是管理员
        UidPidAuId uidPidAuId = new UidPidAuId(userId, project.getProjectId(), ConstantFiledUtil.ADMIN_ID);
        boolean auInsert = uidPidAuidService.InsertUidPidAuid(uidPidAuId);

        if (!auInsert){
            return ResultUtil.error(StatusCode.BadRequest,"加入失败！");
        }

        // 将加入的项目里的items的权限给当前账号

        List<TaskItem> taskItems = taskItemService.GetTaskItemListByProjectId(project.getProjectId());

        for (TaskItem e : taskItems){
            int itemId = e.getItemId();
            // 默认是Admin
            UidTidAuid uidTidAuid = new UidTidAuid(userId,itemId,ConstantFiledUtil.ADMIN_ID);
            uidTidAuidService.InsertUidTidAuid(uidTidAuid);
        }

        if (insert && auInsert){
            return ResultUtil.success("成功加入项目：" + project.getProjectName());
        }
        return ResultUtil.error(StatusCode.BadRequest,"加入失败！");
    }
}
