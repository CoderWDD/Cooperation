package com.example.cooperationproject.controller.projectController;


import com.example.cooperationproject.service.ProjectService;
import com.example.cooperationproject.service.UidPidAuidService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 根据projectId删除project
     * @param projectId
     * @return
     */
    @ResponseBody
    @PostMapping("/project/delete/{projectId}")
    public Message deleteProject(@PathVariable(value = "projectId") Integer projectId){
        String token = request.getHeader("token");
        int userId = myJwtUtil.getUserIdFromToken(token);
        String username = myJwtUtil.getUsernameFromToken(token);

        boolean deleteSuccessful = projectService.DeleteProjectById(projectId, userId, username);

        if (deleteSuccessful){
            return ResultUtil.success("删除Id为：" + projectId + " 的项目成功！");
        }

        return ResultUtil.error(StatusCode.BadRequest,"删除失败！");
    }
}
