package com.example.cooperationproject.controller.projectController;

import com.example.cooperationproject.pojo.Project;
import com.example.cooperationproject.pojo.UidPid;
import com.example.cooperationproject.service.ProjectService;
import com.example.cooperationproject.service.UidPidService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class GetProjectListController {

    private final HttpServletRequest request;

    private final MyJwtUtil myJwtUtil;

    private final ProjectService projectService;

    private final UidPidService uidPidService;

    public GetProjectListController(HttpServletRequest request, MyJwtUtil myJwtUtil, ProjectService projectService, UidPidService uidPidService) {
        this.request = request;
        this.myJwtUtil = myJwtUtil;
        this.projectService = projectService;
        this.uidPidService = uidPidService;
    }

    /**
     * 获取当前账号下的所有project
     * @return
     */
    @ResponseBody
    @GetMapping("/project/getList")
    public Message getProjectList(){

        String token = request.getHeader("token");

        int userId = myJwtUtil.getUserIdFromToken(token);

        List<UidPid> uidPids = uidPidService.GetPidListByUid(userId);

        List<Project> res = new ArrayList<>();

        for (UidPid e : uidPids){
            Project project = projectService.FindProjectById(e.getProjectId());
            if (!Objects.isNull(project)){
                // 如果找到结果
                res.add(project);
            }
        }

        return ResultUtil.success(res);
    }
}
