package com.example.cooperationproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cooperationproject.pojo.UidPid;

import java.util.List;

public interface UidPidService extends IService<UidPid> {
    boolean InsertByUidPid(Integer Uid,Integer Pid);

    boolean DeleteByUidPid(Integer Uid,Integer Pid);

    List<UidPid> GetPidListByUid(Integer Uid);

    List<UidPid> GetUidListByPid(Integer Pid);
}
