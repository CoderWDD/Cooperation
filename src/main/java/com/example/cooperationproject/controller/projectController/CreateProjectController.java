package com.example.cooperationproject.controller.projectController;


import com.example.cooperationproject.pojo.Project;
import com.example.cooperationproject.service.ProjectService;
import com.example.cooperationproject.service.UidPidService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class CreateProjectController {

    private final HttpServletRequest request;

    private final MyJwtUtil myJwtUtil;

    private final ProjectService projectService;


    public CreateProjectController(HttpServletRequest request, MyJwtUtil myJwtUtil, ProjectService projectService) {
        this.request = request;
        this.myJwtUtil = myJwtUtil;
        this.projectService = projectService;
    }

    /**
     * 创建project
     * @param project
     * @return
     */
    @ResponseBody
    @PostMapping("/project/create")
    public Message createProject(@RequestBody Project project){
        String token = request.getHeader("token");
        String username = myJwtUtil.getUsernameFromToken(token);
        int userId = myJwtUtil.getUserIdFromToken(token);

        Project projectFound = projectService.FindProjectByName(project.getProjectName(), username);
        if (!Objects.isNull(projectFound)){
            // 如果项目已经存在
            return ResultUtil.error(StatusCode.BadRequest,"任务已存在！");
        }

        // 设置项目的基本信息
        project.setAuthor(username);
        project.setStatus("未完成");
        boolean isCreated = projectService.CreateProject(project,userId);
        if (isCreated){
            return ResultUtil.success("项目创建成功！");
        }
        return ResultUtil.error(StatusCode.BadRequest,"项目创建失败！");
    }
}
