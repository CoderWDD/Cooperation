package com.example.cooperationproject.controller.projectController;


import com.example.cooperationproject.pojo.Project;
import com.example.cooperationproject.service.ProjectService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class GetProjectInfoController {

    private final HttpServletRequest request;

    private final MyJwtUtil myJwtUtil;

    private final ProjectService projectService;

    public GetProjectInfoController(HttpServletRequest request, MyJwtUtil myJwtUtil, ProjectService projectService) {
        this.request = request;
        this.myJwtUtil = myJwtUtil;
        this.projectService = projectService;
    }

    @GetMapping("/project/getProjectInfo")
    public Message getProjectInfo(@RequestBody Project project){
        String token = request.getHeader("token");
        String username = myJwtUtil.getUsernameFromToken(token);

        Project findProjectByName = projectService.FindProjectByName(project.getProjectName(), username);

        if (Objects.isNull(findProjectByName)){
            // 如果根据项目名找不到
            Project findProjectById = projectService.FindProjectById(project.getProjectId());
            if (Objects.isNull(findProjectById)){
                // 如果两种都找不到
                return ResultUtil.error(StatusCode.BadRequest,"项目信息错误！");
            }else {
                // 如果找到
                if (findProjectById.equals(username)){
                    // 如果作者名和id匹配
                    return ResultUtil.success(findProjectById);
                }else {
                    return ResultUtil.error(StatusCode.BadRequest,"项目Id出错！");
                }
            }
        }

        return ResultUtil.success(findProjectByName);
    }
}
