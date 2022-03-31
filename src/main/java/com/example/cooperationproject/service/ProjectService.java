package com.example.cooperationproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cooperationproject.entity.NewProjectInfo;
import com.example.cooperationproject.pojo.Project;

public interface ProjectService extends IService<Project> {
    boolean CreateProject(Project project,Integer userId);

    Project FindProjectByName(String projectName,String author);

    Project FindProjectById(Integer projectId);

    Project FindProjectByInvitationCode(String invitationCode);

    boolean DeleteProjectByName(String projectName,Integer userId,String username);

    boolean DeleteProjectById(Integer projectId,Integer userId,String username);

    boolean ModifyProject(NewProjectInfo project,Integer projectId,String invitationCode, String username);
}
