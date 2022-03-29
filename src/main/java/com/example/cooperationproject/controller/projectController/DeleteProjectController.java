package com.example.cooperationproject.controller.projectController;


import com.example.cooperationproject.constant.AuthenticationConstant;
import com.example.cooperationproject.pojo.Project;
import com.example.cooperationproject.pojo.UidPidAuId;
import com.example.cooperationproject.service.ProjectService;
import com.example.cooperationproject.service.UidPidAuidService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class DeleteProjectController {
    private final UidPidAuidService uidPidAuidService;

    private final HttpServletRequest request;

    private final MyJwtUtil myJwtUtil;

    private final ProjectService projectService;

    public DeleteProjectController(HttpServletRequest request, MyJwtUtil myJwtUtil, ProjectService projectService, UidPidAuidService uidPidAuidService) {
        this.request = request;
        this.myJwtUtil = myJwtUtil;
        this.projectService = projectService;
        this.uidPidAuidService = uidPidAuidService;
    }

    // TODO : 权限认证

    @PostMapping("/project/deleteProject")
    public Message deleteProject(@RequestBody Project project){
        String token = request.getHeader("token");
        int userId = myJwtUtil.getUserIdFromToken(token);
        String username = myJwtUtil.getUsernameFromToken(token);

        UidPidAuId uidPidAuId = uidPidAuidService.FindUidPidAuidByUidPid(userId, project.getProjectId());

        if (Objects.isNull(uidPidAuId) || uidPidAuId.getAuId() == AuthenticationConstant.USER){
            // 如果是没有权限或者user权限，都不能删除
            return ResultUtil.error(StatusCode.Forbidden,"没有权限！");
        }

        boolean deleteSuccessful = projectService.DeleteProjectByName(project.getProjectName(), userId,username);
        if (deleteSuccessful){
            return ResultUtil.success("删除项目：" + project.getProjectName() + "成功！");
        }

        deleteSuccessful = projectService.DeleteProjectById(project.getProjectId(), userId, username);

        if (deleteSuccessful){
            return ResultUtil.success("删除Id为：" + project.getProjectId() + " 的项目成功！");
        }

        return ResultUtil.error(StatusCode.BadRequest,"删除失败！");
    }
}
