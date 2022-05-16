package com.example.cooperationproject.controller.projectController;

import com.example.cooperationproject.pojo.Project;
import com.example.cooperationproject.pojo.UidPid;
import com.example.cooperationproject.service.ProjectService;
import com.example.cooperationproject.service.UidPidService;
import com.example.cooperationproject.service.UserService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final UserService userService;

    public GetProjectListController(HttpServletRequest request, MyJwtUtil myJwtUtil, ProjectService projectService, UidPidService uidPidService, UserService userService) {
        this.request = request;
        this.myJwtUtil = myJwtUtil;
        this.projectService = projectService;
        this.uidPidService = uidPidService;
        this.userService = userService;
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

        String usernameInToken = myJwtUtil.getUsernameFromToken(token);

        List<UidPid> uidPids = uidPidService.GetPidListByUid(userId);

        List<Project> res = new ArrayList<>();

        for (UidPid e : uidPids){
            Project project = projectService.FindProjectById(e.getProjectId());
            if (!Objects.isNull(project)){
                // 如果找到结果

                // 获取project的cooperators，并封装成list

                List<UidPid> uidPidList = uidPidService.GetUidListByPid(e.getProjectId());

                List<String> cooperators = new ArrayList<>();

                for (UidPid temp : uidPidList){
                    // 只获取不包括自己在内的cooperators的list
                    if (temp.getUserId() == e.getUserId())continue;

                    String username = userService.FindUsernameByUserId(temp.getUserId());
                    // 只获取合作伙伴，不获取当前用户
                    if (!Objects.isNull(username) && !username.isEmpty() && !username.equals(usernameInToken)) {
                        cooperators.add(username);
                    }
                }
                project.setCooperators(cooperators);
                res.add(project);
            }
        }

        return ResultUtil.success(res);
    }
}
