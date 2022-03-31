package com.example.cooperationproject.controller.projectController;

import com.example.cooperationproject.constant.ConstantFiledUtil;
import com.example.cooperationproject.pojo.Project;
import com.example.cooperationproject.pojo.UidPidAuId;
import com.example.cooperationproject.service.ProjectService;
import com.example.cooperationproject.service.UidPidAuidService;
import com.example.cooperationproject.service.UidPidService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class JoinProjectController {

    private final UidPidAuidService uidPidAuidService;

    private final UidPidService uidPidService;

    private final ProjectService projectService;

    private final HttpServletRequest request;

    private final MyJwtUtil myJwtUtil;

    public JoinProjectController(UidPidService uidPidService, ProjectService projectService, HttpServletRequest request, MyJwtUtil myJwtUtil, UidPidAuidService uidPidAuidService) {
        this.uidPidService = uidPidService;
        this.projectService = projectService;
        this.request = request;
        this.myJwtUtil = myJwtUtil;
        this.uidPidAuidService = uidPidAuidService;
    }

    @PostMapping("/project/joinProject/{invitationCode}")
    public Message joinProject(@PathVariable(value = "invitationCode") String invitationCode){
        Project project = projectService.FindProjectByInvitationCode(invitationCode);

        if (Objects.isNull(project)){
            return ResultUtil.error(StatusCode.BadRequest,"邀请码出错！");
        }

        String token = request.getHeader("token");
        int userId = myJwtUtil.getUserIdFromToken(token);

        boolean insert = uidPidService.InsertByUidPid(userId, project.getProjectId());

        // 被邀请进了项目，还需要将其权限写入数据库

        // 默认被邀请进去都是管理员
        UidPidAuId uidPidAuId = new UidPidAuId(userId, project.getProjectId(), ConstantFiledUtil.ADMIN_ID);
        boolean auInsert = uidPidAuidService.InsertUidPidAuid(uidPidAuId);

        if (insert && auInsert){
            return ResultUtil.success("成功加入项目：" + project.getProjectName());
        }
        return ResultUtil.error(StatusCode.BadRequest,"加入失败！");
    }
}
