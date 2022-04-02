package com.example.cooperationproject.controller.projectController;

import com.example.cooperationproject.pojo.UidPid;
import com.example.cooperationproject.service.UidPidService;
import com.example.cooperationproject.service.UserService;
import com.example.cooperationproject.utils.MyJwtUtil;
import com.example.cooperationproject.utils.ResultUtil;
import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class GetCooperatorController {

    private final UidPidService uidPidService;

    private final UserService userService;

    private final HttpServletRequest request;

    private final MyJwtUtil myJwtUtil;

    public GetCooperatorController(UidPidService uidPidService, UserService userService, HttpServletRequest request, MyJwtUtil myJwtUtil) {
        this.uidPidService = uidPidService;
        this.userService = userService;
        this.request = request;
        this.myJwtUtil = myJwtUtil;
    }


    /**
     * 获取当前项目中除自己外的其他合作者的名单
     * @param projectId
     * @return
     */
    @ResponseBody
    @GetMapping("/project/getCooperator/{projectId}")
    public Message getProjectCooperator(@PathVariable(value = "projectId") Integer projectId){
        if (Objects.isNull(projectId)){
            return ResultUtil.error(StatusCode.BadRequest,"projectId不能为空！");
        }

        List<UidPid> uidPidList = uidPidService.GetUidListByPid(projectId);

        if (Objects.isNull(uidPidList)){
            return ResultUtil.error(StatusCode.BadRequest,"获取合作者失败！");
        }

        String token = request.getHeader("token");
        String usernameInToken = myJwtUtil.getUsernameFromToken(token);

        List<String> res = new ArrayList<>();

        for (UidPid e : uidPidList){
            String username = userService.FindUsernameByUserId(e.getUserId());
            // 只获取合作伙伴，不获取当前用户
            if (!Objects.isNull(username) && !username.isEmpty() && !username.equals(usernameInToken)) {
                res.add(username);
            }
        }
        return ResultUtil.success(res);
    }

}
