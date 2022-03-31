package com.example.cooperationproject.controller.projectController;


import com.example.cooperationproject.pojo.Project;
import com.example.cooperationproject.service.ProjectService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/project/get/{projectId}")
    public Message getProjectInfo(@PathVariable(value = "projectId") Integer projectId){
        String token = request.getHeader("token");
        String username = myJwtUtil.getUsernameFromToken(token);

        Project findProjectById = projectService.FindProjectById(projectId);
        if (Objects.isNull(findProjectById)){
            // 如果找不到
            return ResultUtil.error(StatusCode.BadRequest,"项目ID信息错误！");
        }else {
            // 如果找到
            return ResultUtil.success(findProjectById);
        }
    }
}
