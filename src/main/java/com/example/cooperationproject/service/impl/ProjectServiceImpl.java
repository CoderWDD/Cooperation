package com.example.cooperationproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cooperationproject.constant.ConstantFiledUtil;
import com.example.cooperationproject.entity.NewProjectInfo;
import com.example.cooperationproject.pojo.Project;
import com.example.cooperationproject.mapper.ProjectMapper;
import com.example.cooperationproject.pojo.UidPidAuId;
import com.example.cooperationproject.service.ProjectService;
import com.example.cooperationproject.service.UidPidAuidService;
import com.example.cooperationproject.service.UidPidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Autowired
    private UidPidService uidPidService;

    @Autowired
    private UidPidAuidService uidPidAuidService;

    @Override
    public boolean CreateProject(Project project,Integer userId) {
        boolean saveSuccessful = save(project);

        if (!saveSuccessful){
            // 如果创建项目不成功
            return false;
        }

        // 创建成功，则从数据库中获取自动生成的projectId
        Project projectFound = FindProjectByName(project.getProjectName(), project.getAuthor());
        if (Objects.isNull(projectFound)){
            return false;
        }

        int projectId = projectFound.getProjectId();

        boolean insertSuccessful = uidPidService.InsertByUidPid(userId, projectId);

        if (!insertSuccessful){
            // 如果在关联表中插入不成功
            // 到这一步了，说明在项目表中插入成功了，需要将插入项目表中的数据删除
            removeById(projectId);
            return false;
        }

        UidPidAuId uidPidAuId = new UidPidAuId(userId,projectId, ConstantFiledUtil.AUTHOR_ID);
        boolean uidPidAuid = uidPidAuidService.InsertUidPidAuid(uidPidAuId);

        // 能运行到了，说明一定成功了
        return saveSuccessful && insertSuccessful && uidPidAuid;
    }

    @Override
    public Project FindProjectByName(String projectName, String author) {
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        wrapper.eq("project_name",projectName);

        Project projectFound = getOne(wrapper);

        return projectFound;
    }

    @Override
    public Project FindProjectById(Integer projectId) {
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        wrapper.eq("project_id",projectId);

        Project projectFound = getOne(wrapper);

        return projectFound;
    }

    @Override
    public Project FindProjectByInvitationCode(String invitationCode) {
        QueryWrapper<Project> wrapper = new QueryWrapper<>();

        wrapper.eq("invitation_code",invitationCode);

        Project project = getOne(wrapper);

        return project;
    }

    @Override
    public boolean DeleteProjectByName(String projectName, Integer userId,String username) {
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        wrapper.eq("project_name",projectName);
        wrapper.eq("author",username);

        Project projectFound = getOne(wrapper);

        if (Objects.isNull(projectFound)){
            // 如果项目不存在
            return false;
        }

        int projectId = projectFound.getProjectId();
        boolean removeSuccessful = remove(wrapper);

        if (removeSuccessful){
            // 如果成功删除了项目，则应该把关联表中的关系也删除
            uidPidService.DeleteByUidPid(userId,projectId);
            uidPidAuidService.DeleteUidPidAuid(userId,projectId);
            // 不考虑删除失败的情况。有时间再来加
            return true;
        }
        // 跑到这里说明没有全部删除成功
        return false;
    }

    @Override
    public boolean DeleteProjectById(Integer projectId, Integer userId, String username) {
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        wrapper.eq("project_id",projectId);
        wrapper.eq("author",username);

        Project projectFound = getOne(wrapper);

        if (Objects.isNull(projectFound)){
            // 如果项目不存在
            return false;
        }

        boolean removeSuccessful = remove(wrapper);

        if (removeSuccessful){
            // 如果成功删除了项目，则应该把关联表中的关系也删除
            uidPidService.DeleteByUidPid(userId,projectId);
            uidPidAuidService.DeleteUidPidAuid(userId,projectId);
            return true;
        }

        return false;
    }


    @Override
    public boolean ModifyProject(NewProjectInfo newProjectInfo,Integer projectId,String invitationCode, String username) {

        Project project = new Project();
        project.setProjectId(projectId);
        project.setProjectName(newProjectInfo.getProjectName());
        project.setProjectTime(newProjectInfo.getProjectTime());
        project.setDescription(newProjectInfo.getDescription());
        project.setInvitationCode(invitationCode);
        project.setStatus(newProjectInfo.getStatus());

        boolean updateSuccessful = updateById(project);

        return updateSuccessful;
    }
}
