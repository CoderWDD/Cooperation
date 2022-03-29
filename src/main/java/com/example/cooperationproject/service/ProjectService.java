package com.example.cooperationproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cooperationproject.pojo.Project;

public interface ProjectService extends IService<Project> {
    boolean CreateProject(Project project,Integer userId);

    Project FindProjectByName(String projectName,String author);

    Project FindProjectById(Integer projectId);

    Project FindProjectByInvitationCode(Integer invitationCode);

    boolean DeleteProjectByName(String projectName,Integer userId,String username);

    boolean DeleteProjectById(Integer projectId,Integer userId,String username);

    boolean ModifyProject(Project project,String username);
}
