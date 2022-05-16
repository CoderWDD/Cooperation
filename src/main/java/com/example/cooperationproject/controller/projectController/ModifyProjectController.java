package com.example.cooperationproject.controller.projectController;

import com.example.cooperationproject.entity.NewProjectInfo;
import com.example.cooperationproject.pojo.Project;
import com.example.cooperationproject.service.ProjectService;
import com.example.cooperationproject.service.UidPidAuidService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class ModifyProjectController {

    private final UidPidAuidService uidPidAuidService;

    private final HttpServletRequest request;

    private final MyJwtUtil myJwtUtil;

    private final ProjectService projectService;

    public ModifyProjectController(HttpServletRequest request, MyJwtUtil myJwtUtil, ProjectService projectService, UidPidAuidService uidPidAuidService) {
        this.request = request;
        this.myJwtUtil = myJwtUtil;
        this.projectService = projectService;
        this.uidPidAuidService = uidPidAuidService;
    }

    /**
     * 根据projectId修改project信息
     * @param projectId
     * @param newProjectInfo
     * @return
     */
    @ResponseBody
    @PostMapping("/project/modify/{projectId}")
    public Message modifyProject(@PathVariable(value = "projectId") Integer projectId, @RequestBody NewProjectInfo newProjectInfo){
        String token = request.getHeader("token");

        String username = myJwtUtil.getUsernameFromToken(token);
        int userId = myJwtUtil.getUserIdFromToken(token);

        // 只能是通过projectId进行修改，因为项目名是可以改变的
        Project projectById = projectService.FindProjectById(projectId);

        if (Objects.isNull(projectById)){
            return ResultUtil.error(StatusCode.BadRequest,"项目ID不存在！");
        }

        // 只有创建者，才能修改project info
        if (!projectById.getAuthor().equals(username)){
            return ResultUtil.error(StatusCode.Forbidden,"权限不够！");
        }


        // 到这里，说明项目一定是存在的
        boolean modifyProjectSuccessful = projectService.ModifyProject(newProjectInfo,projectId,projectById.getInvitationCode(), username);
        if (modifyProjectSuccessful){
            return ResultUtil.success("项目：" + newProjectInfo.getProjectName() + "，修改成功！");
        }
        return ResultUtil.error(StatusCode.BadRequest,"项目信息修改失败！");
    }
}
