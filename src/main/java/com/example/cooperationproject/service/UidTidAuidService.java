package com.example.cooperationproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cooperationproject.pojo.UidTidAuid;

import java.util.List;

public interface UidTidAuidService extends IService<UidTidAuid> {

    boolean InsertUidTidAuid(UidTidAuid uidTidAuid);

    UidTidAuid FindAuidByUidTid(Integer userId,Integer itemId);

    List<UidTidAuid> FindAuidListByProjectId(Integer projectId);

    boolean DeleteByUIDTID(Integer userId,Integer itemId);

    boolean DeleteByTID(Integer itemId);
}
