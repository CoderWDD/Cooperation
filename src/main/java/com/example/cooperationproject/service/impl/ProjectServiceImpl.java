package com.example.cooperationproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cooperationproject.pojo.Project;
import com.example.cooperationproject.mapper.ProjectMapper;
import com.example.cooperationproject.service.ProjectService;
import com.example.cooperationproject.service.UidPidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Autowired
    private UidPidService uidPidService;

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
        // 能运行到了，说明一定成功了
        return true;
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
    public Project FindProjectByInvitationCode(Integer invitationCode) {
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
            return uidPidService.DeleteByUidPid(userId,projectId);
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
            return uidPidService.DeleteByUidPid(userId,projectId);
        }

        return true;
    }


    @Override
    public boolean ModifyProject(Project project, String username) {
        UpdateWrapper<Project> wrapper = new UpdateWrapper<>();

        wrapper.eq("project_id",project.getProjectId());
        wrapper.eq("author",username);

        boolean updateSuccessful = update(project, wrapper);

        return updateSuccessful;
    }
}
